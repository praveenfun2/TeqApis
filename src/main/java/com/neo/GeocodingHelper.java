package com.neo;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.*;
import com.neo.DAO.APIDAO;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Users.Courier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.google.maps.model.AddressComponentType.*;
import static com.neo.Utils.GeneralHelper.isNotEmpty;

/**
 * Created by localadmin on 6/7/17.
 */
@Component
public class GeocodingHelper {

    private APIDAO apidao;
    private boolean apiChanged = true;
    private String clientID = "";
    private GeoApiContext geoApiContext;
    private final String SHORT_NAME_UK = "GB";

    @Autowired
    public GeocodingHelper(APIDAO apidao) {
        this.apidao = apidao;
    }

    public LatLng getLatLng(Address address) {
        return getGeocodingResult(address).geometry.location;
    }

    public LatLng getLatLng(String addressLine, String postalCode) {
        try {
            Address address = new Address();
            address.setAddressLine(addressLine);
            address.setPostalCode(postalCode);
            return getGeocodingResult(address).geometry.location;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    private GeocodingResult getGeocodingResult(Double lat, Double lng) {
        if (lat == null || lng == null) return null;
        GeocodingResult[] results = GeocodingApi.reverseGeocode(getGeoApiContext(), new LatLng(lat, lng))
                .awaitIgnoreError();
        return results[0];
    }

    private GeocodingResult getGeocodingResult(Address address) {
        if (address == null) return null;

        String city = address.getCity();
        String state = address.getState();
        String country = address.getCountry();
        String pincode = address.getPostalCode();

        ArrayList<ComponentFilter> filters = new ArrayList<>();

        if (isNotEmpty(city))
            filters.add(ComponentFilter.locality(city));
        if (isNotEmpty(state))
            filters.add(ComponentFilter.administrativeArea(state));
        if (isNotEmpty(country))
            filters.add(ComponentFilter.country(country));
        if (isNotEmpty(pincode))
            filters.add(ComponentFilter.postalCode(pincode));

        GeocodingResult[] results = GeocodingApi.geocode(getGeoApiContext(), address.getAddressLine())
                .components(filters.toArray(new ComponentFilter[0]))
                .awaitIgnoreError();
        return results[0];
    }

    public CSCLL getCSCLL(Address address) {
        GeocodingResult result = getGeocodingResult(address);
        return new CSCLL(getLongCSC(result.addressComponents), result.geometry.location);
    }

    public class CSCLL {
        private String[] csc;
        private LatLng latLng;

        public CSCLL(String[] csc, LatLng latLng) {
            this.csc = csc;
            this.latLng = latLng;
        }

        public String[] getCsc() {
            return csc;
        }

        public void setCsc(String[] csc) {
            this.csc = csc;
        }

        public LatLng getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }
    }

    public String[] getShortCSC(Double lat, Double lng) {
        AddressComponent[] addressComponents = getCSC(getGeocodingResult(lat, lng).addressComponents);
       return getShortCSC(addressComponents);
    }

    public String[] getShortCSC(AddressComponent[] addressComponents) {
        String[] strings = new String[3];
        for (int i = 0; i < 3; i++) {
            AddressComponent ac = addressComponents[i];
            if (ac != null)
                strings[i] = ac.shortName;
        }
        return strings;
    }

    private AddressComponent[] getCSC(AddressComponent[] addressComponents) {
        AddressComponent[] strings = new AddressComponent[3];
        if (addressComponents == null) return strings;

        AddressComponent countryAddressComponent = getAddressComponent(COUNTRY, addressComponents);
        assert countryAddressComponent != null;
        strings[2] = countryAddressComponent;

        AddressComponentType stateAddressComponentType;
        switch (countryAddressComponent.shortName) {
            case SHORT_NAME_UK:
                stateAddressComponentType = ADMINISTRATIVE_AREA_LEVEL_2;
                break;
            default:
                stateAddressComponentType = ADMINISTRATIVE_AREA_LEVEL_1;
                break;
        }
        AddressComponent stateAddressComponent = getAddressComponent(stateAddressComponentType, addressComponents);
        assert stateAddressComponent != null;
        strings[1] = stateAddressComponent;

        AddressComponent cityAddressComponent = null;
        switch (stateAddressComponentType) {
            case ADMINISTRATIVE_AREA_LEVEL_1:
                cityAddressComponent = getAddressComponent(ADMINISTRATIVE_AREA_LEVEL_2, addressComponents);
                break;
            case ADMINISTRATIVE_AREA_LEVEL_2:
                cityAddressComponent = getAddressComponent(ADMINISTRATIVE_AREA_LEVEL_3, addressComponents);
                break;
            case ADMINISTRATIVE_AREA_LEVEL_3:
                cityAddressComponent = getAddressComponent(ADMINISTRATIVE_AREA_LEVEL_4, addressComponents);
                break;
        }
        if (cityAddressComponent == null)
            cityAddressComponent = getAddressComponent(new AddressComponentType[]{LOCALITY}, addressComponents);
        if (cityAddressComponent == null) cityAddressComponent = getAddressComponent(POSTAL_TOWN, addressComponents);
        assert cityAddressComponent != null;
        strings[0] = cityAddressComponent;

        return strings;
    }

    public String[] getLongCSC(Double lat, Double lng) {
        AddressComponent[] addressComponents = getCSC(getGeocodingResult(lat, lng).addressComponents);
        return getLongCSC(addressComponents);
    }

    public String[] getLongCSC(AddressComponent[] addressComponents) {
        String[] strings = new String[3];
        for (int i = 0; i < 3; i++) {
            AddressComponent ac = addressComponents[i];
            if (ac != null)
                strings[i] = ac.longName;
        }
        return strings;
    }

    private GeoApiContext getGeoApiContext() {
        if (apiChanged) {
            clientID = apidao.geocodingApi().getId();
            geoApiContext = new GeoApiContext().setApiKey(clientID);
            apiChanged = false;
        }
        return geoApiContext;
    }

    private AddressComponent getAddressComponent(AddressComponentType addressComponentType,
                                                 AddressComponent[] addressComponents) {
        for (AddressComponent addressComponent : addressComponents) {
            AddressComponentType[] types = addressComponent.types;
            for (AddressComponentType type : types) {
                if (type == addressComponentType)
                    return addressComponent;
            }
        }

        return null;
    }

    private AddressComponent getAddressComponent(AddressComponentType[] addressComponentTypes,
                                                 AddressComponent[] addressComponents) {
        for (AddressComponentType addressComponentType : addressComponentTypes) {
            AddressComponent component = getAddressComponent(addressComponentType, addressComponents);
            if (component != null) return component;
        }

        return null;
    }

    public double distanceInKm(double lat1, double lat2, double lon1,
                               double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }

    public double distanceInMiles(double lat1, double lat2, double lon1,
                                  double lon2) {

        return distanceInKm(lat1, lat2, lon1, lon2) * 0.621371;
    }

    public int getCoverage(Address delivery, Address pickup) {
        CSCLL d = getCSCLL(delivery);

        if (d.getCsc()[0].equals(pickup.getCity())) return Courier.CITY_COVERAGE_KEY;
        if (d.getCsc()[1].equals(pickup.getState())) return Courier.STATE_COVERAGE_KEY;
        if (d.getCsc()[2].equals(pickup.getCountry())) return Courier.COUNTRY_COVERAGE_KEY;

        return Courier.INTERNATIONAL_COVERAGE_KEY;
    }

    public void setApiChanged() {
        this.apiChanged = true;
    }
}
