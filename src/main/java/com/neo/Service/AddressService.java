package com.neo.Service;

import com.google.maps.model.LatLng;
import com.neo.DAO.AddressDAO;
import com.neo.DatabaseModel.Address;
import com.neo.GeocodingHelper;
import com.neo.Model.RAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

@Service
@Transactional
public class AddressService {

    private final GeocodingHelper geocodingHelper;

    @Autowired
    public AddressService(GeocodingHelper geocodingHelper) {
        this.geocodingHelper = geocodingHelper;
    }

    public void update(RAddress rAddress, Address address) {
        if (rAddress == null || !isNotEmpty(rAddress.getAddressLine())) return;

        LatLng location = geocodingHelper.getLatLng(rAddress.getAddressLine(),
                rAddress.getPostalCode());
        if (location == null) return;

        String[] strings = geocodingHelper.getLongCSC(location.lat, location.lng);
        if (strings[0] == null) return;

        address.setAddressLine(rAddress.getAddressLine());
        address.setCountry(strings[2]);
        address.setState(strings[1]);
        address.setCity(strings[0]);
        address.setPostalCode(rAddress.getPostalCode());
        address.setLat(location.lat);
        address.setLng(location.lng);
    }

    public void buildRAddress(Address address, RAddress rAddress){
        rAddress.setAddressLine(address.getAddressLine());
        rAddress.setPostalCode(address.getPostalCode());
        rAddress.setCity(address.getCity());
        rAddress.setState(address.getState());
        rAddress.setCountry(address.getCountry());
    }
    public boolean isValid(Address ad) {
        return ad != null && isNotEmpty(ad.getCity()) && isNotEmpty(ad.getState()) && isNotEmpty(ad.getCountry())
                && isNotEmpty(ad.getAddressLine()) && isNotEmpty(ad.getPostalCode()) && ad.getLat() != null && ad.getLng() != null;
    }

    public String addressString(Address a) {
        String[] ar = new String[]{a.getAddressLine(), a.getCity(), a.getState(), a.getCountry(), a.getPostalCode()};
        return addressString(ar);
    }

    private String addressString(String[] ar) {
        int i = 0;
        while (!isNotEmpty(ar[i])) i++;
        StringBuilder s = new StringBuilder(ar[i]);
        for (i = i + 1; i < ar.length; i++)
            if (ar[i] != null)
                s.append(", ").append(ar[i]);
        return s.toString();
    }

}
