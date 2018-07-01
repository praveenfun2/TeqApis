/*
package com.neo.Controller.Courier;

import com.google.maps.model.LatLng;
import com.neo.DAO.CourierDAO;
import com.neo.DAO.DeliveryTypeDAO;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.CourierCoverage;
import com.neo.DatabaseModel.CourierDeliveryType;
import com.neo.DatabaseModel.Shipment.DeliveryType;
import com.neo.DatabaseModel.Users.Courier;
import com.neo.GeocodingHelper;
import com.neo.Model.RAddress;
import com.neo.Model.RCourier;
import com.neo.Model.RCoverage;
import com.neo.Model.RDeliveryType;
import com.neo.Service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/courier")
public class CourierController {

    private final CourierDAO courierDAO;
    private final GeocodingHelper geocodingHelper;
    private final DeliveryTypeDAO deliveryTypeDAO;
    private final AddressService addressService;

    public CourierController(CourierDAO courierDAO, GeocodingHelper geocodingHelper, DeliveryTypeDAO deliveryTypeDAO,
                             AddressService addressService) {
        this.courierDAO = courierDAO;
        this.geocodingHelper = geocodingHelper;
        this.deliveryTypeDAO = deliveryTypeDAO;
        this.addressService = addressService;
    }

    @RequestMapping("")
    public RCourier getCourier(@RequestAttribute String id) throws SQLException {
        Courier courier = courierDAO.get(id);

        double rating = courier.getRating() / (double) courier.getRatingCount();
        RCourier rCourier = new RCourier(courier.getName(), courier.getDescription(), courier.getId(), courier.getPaypal(), courier.isValidated(), rating);
        for (CourierCoverage courierCoverage : courier.getCoverages())
            rCourier.addCoverage(courierCoverage);
        for (CourierDeliveryType courierDeliveryType : courier.getDeliveryTypes())
            rCourier.addDeliveryType(courierDeliveryType);

        Address a = courier.getAddress();
        RAddress rAddress = new RAddress();
        if (a != null) {
            rAddress.setAddressString(addressService.addressStringWithoutPhoneAndName(a));
            rAddress.build(a);
        }
        rCourier.setAddress(rAddress);

        return rCourier;
    }

    @RequestMapping("/check")
    public boolean checkCourier(@RequestAttribute String id) {
        Courier courier = courierDAO.get(id);
        return courier.isValidated();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateCourier(@RequestBody RCourier rCourier, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Courier courier = courierDAO.get(id);

        LatLng location = geocodingHelper.getLatLng(rCourier.getAddress().getAddressLine(),
                rCourier.getAddress().getPostalCode());
        if (location == null) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        } else if (courier.getAddress() == null && rCourier.getAddress() == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        courier.setDescription(rCourier.getDescription());
        courier.setName(rCourier.getName());
        courier.setPaypal(rCourier.getPaypal());
        courier.setValidated(courierDAO.verified(courier));

        courier.getCoverages().clear();
        HashMap<Long, Float> m = new HashMap<>();
        for (RCoverage rCoverage : rCourier.getCoverages()) {
            int type = rCoverage.getCoverage();
            float price = rCoverage.getPrice();
            m.put((long) type, ((int)(price * 100)) / 100f);
        }
        Set<Map.Entry<Long, Float>> set = m.entrySet();
        for (Map.Entry<Long, Float> e : set) {
            int cov = (int)((long) e.getKey());
            if (cov >= Courier.CITY_COVERAGE_KEY && cov <= Courier.INTERNATIONAL_COVERAGE_KEY)
                courier.getCoverages().add(new CourierCoverage(cov, courier, e.getValue()));
        }

        m.clear();

        courier.getDeliveryTypes().clear();
        for (RDeliveryType rDeliveryType : rCourier.getDeliveryTypes()) {
            long type = rDeliveryType.getType();
            float price = rDeliveryType.getPrice();
            m.put(type, ((int) (price * 100)) / 100f);
        }
        set = m.entrySet();
        for (Map.Entry<Long, Float> e : set) {
            DeliveryType d = deliveryTypeDAO.get(e.getKey());
            if (d != null)
                courier.getDeliveryTypes().add(new CourierDeliveryType(d, courier, e.getValue()));
        }


        Address address1 = courier.getAddress();
        if (address1 == null)
            address1 = new Address();

        RAddress rAddress = rCourier.getAddress();

        String[] strings = geocodingHelper.getCSC(location.lat, location.lng);

        address1.setAddressLine(rAddress.getAddressLine());
        address1.setPhone(rAddress.getPhone());
        address1.setCountry(strings[2]);
        address1.setState(strings[1]);
        address1.setCity(strings[0]);
        address1.setPostalCode(rAddress.getPostalCode());

        courier.setAddress(address1);

        courier.setLat(location.lat);
        courier.setLon(location.lng);

        courierDAO.update(courier);

    }
}
*/
