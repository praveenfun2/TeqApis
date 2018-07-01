/*
package com.neo.Controller.Seller;

import com.neo.DAO.*;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Commission;
import com.neo.DatabaseModel.CourierCoverage;
import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.PaymentOrder;
import com.neo.DatabaseModel.Users.Courier;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.DatabaseModel.Users.User;
import com.neo.GeocodingHelper;
import com.neo.Model.Price;
import com.neo.Model.RCourier;
import com.neo.Model.RCourierOrder;
import com.neo.PayPalHelper;
import com.neo.Service.AddressService;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.neo.DatabaseModel.Order.CourierOrder.*;
import static com.neo.DatabaseModel.Users.Courier.CITY_COVERAGE_KEY;

@CrossOrigin
@RestController("sellerCourierOrderController")
@RequestMapping("/seller/order/courier")
public class CourierOrderController {
    private final CourierOrderDAO courierOrderDAO;
    private final SellerDAO sellerDAO;
    private final CardOrderDAO cardOrderDAO;
    private final CourierDAO courierDAO;
    private final PayPalHelper payPalHelper;
    private final PaymentOrderDAO paymentOrderDAO;
    private final UserDAO userDAO;
    private final GeocodingHelper geocodingHelper;
    private final CommissionDAO commissionDAO;
    private final AddressService addressService;
    private final OrderDAO orderDAO;

    @Autowired
    public CourierOrderController(CourierOrderDAO courierOrderDAO, SellerDAO sellerDAO, CardOrderDAO cardOrderDAO, CourierDAO courierDAO,
                                  PayPalHelper payPalHelper, PaymentOrderDAO paymentOrderDAO, UserDAO userDAO, GeocodingHelper geocodingHelper, CommissionDAO commissionDAO, AddressService addressService, OrderDAO orderDAO) {
        this.courierOrderDAO = courierOrderDAO;
        this.sellerDAO = sellerDAO;
        this.cardOrderDAO = cardOrderDAO;
        this.courierDAO = courierDAO;
        this.payPalHelper = payPalHelper;
        this.paymentOrderDAO = paymentOrderDAO;
        this.userDAO = userDAO;
        this.geocodingHelper = geocodingHelper;
        this.commissionDAO = commissionDAO;
        this.addressService = addressService;
        this.orderDAO = orderDAO;
    }

    public static final String STATUS_RECEIVED="received", STATUS_PICKED_UP="picked up", STATUS_DISPATCHED = "out for delivery",
            STATUS_DELIVERED="Delivered", STATUS_CANCELED="Cancelled";

    @RequestMapping("/book")
    public Long executeOrder(@RequestAttribute String id, @RequestBody RCourierOrder r, HttpServletResponse response) {
        Seller seller = sellerDAO.get(id);
        if (!seller.isValidated()){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        CardOrder cardOrder = cardOrderDAO.get(r.getId());
        Courier courier = courierDAO.get(r.getCourierId());
        if (cardOrder == null || courier==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        CourierOrder courierOrder = cardOrder.getCourierOrder();
        if (courierOrder != null || !cardOrder.getSeller().getId().equals(seller.getId())){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        Address deliveryAddress=cardOrder.getAddress();
        Address sellerAddress=seller.getAddress();
        int coverage=geocodingHelper.getCoverage(deliveryAddress, sellerAddress);
        Map<Integer, Float> m=new HashMap<>();
        for(CourierCoverage cc: courier.getCoverages())
            if(cc!=null) m.put(cc.getCoverage(), cc.getPrice());
        Float bill=m.get(coverage);
        if(bill==null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        */
/*TODO Correct price*//*

        String saleId = payPalHelper.executePayment(r.getPayerID(), r.getPaymentID(), 212561651);
        if (saleId == null){
            response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
            return null;
        }
        PaymentOrder paymentOrder = new PaymentOrder(saleId);
        paymentOrderDAO.save(paymentOrder);




        courierOrder = new CourierOrder(bill, paymentOrder, cardOrder, seller, courier);
        courierOrderDAO.save(courierOrder);
        cardOrder.setCourierOrder(courierOrder);
        cardOrder.setStatus(CourierOrder.STATUS_RECEIVED_KEY);
        cardOrderDAO.update(cardOrder);

        return cardOrder.getId();
    }

    @RequestMapping("/cancel")
    public void cancelOrder(@RequestAttribute String id, @RequestParam Long oid, HttpServletResponse response) {
        Seller seller=sellerDAO.get(id);

        CourierOrder order=courierOrderDAO.get(oid);
        if(order==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }else if(!courierOrderDAO.canSellerCancel(order, seller)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        CardOrder cardOrder=order.getCardOrder();
        cardOrder.setCourierOrder(null);
        int st=cardOrder.getStatus();
        cardOrder.setStatus(CardOrder.STATUS_PRINTED_KEY);
        cardOrderDAO.update(cardOrder);

        order.setStatus(STATUS_CANCELED_KEY);
        courierOrderDAO.update(order);

        boolean refund = payPalHelper.refundPayment(order.getPaymentOrder().getSaleId(),
                order.getPrice());
        if (!refund){
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            cardOrder.setStatus(st);
            cardOrder.setCourierOrder(order);
            cardOrderDAO.update(cardOrder);
        }

    }

    */
/*TODO Correct this*//*

    @RequestMapping("/couriers")
    public List<RCourier> getCouriers(@RequestHeader("Authorization") String token, Long orderId,
                                      @RequestParam(defaultValue = "0") int minprice,
                                      @RequestParam(defaultValue = Integer.MAX_VALUE + "") int maxprice,
                                      @RequestParam(required = false) String order) {
        Seller seller = sellerDAO.get(JWTHelper.decodeToken(token).getId());
        if (seller == null || !seller.isValidated()) return null;

        CardOrder cardOrder=cardOrderDAO.get(orderId);
        if(cardOrder==null) return null;

        if(!cardOrder.getSeller().getId().equals(seller.getId())) return null;

        List<RCourier> rCouriers=new ArrayList<>();

        Address deliveryAddress=cardOrder.getAddress();
        Address sellerAddress=seller.getAddress();
        int coverage=geocodingHelper.getCoverage(deliveryAddress, sellerAddress);

        */
/*List<Courier> couriers=courierService.getFilteredCouriers(minprice, maxprice, order, sellerAddress, deliveryAddress);
        for(Courier c: couriers){
            //double distance= geocodingHelper.distanceInMiles(seller.getLat(), deliveryLatLng.lat, seller.getLon(),deliveryLatLng.lng);
            float price=-1;
            switch (coverage){
                case Courier.CITY_COVERAGE_KEY:
                    price=c.getIntracityPrice();
                    break;
                case Courier.STATE_COVERAGE_KEY:
                    price=c.getIntercityPrice();
                    break;
                case Courier.COUNTRY_COVERAGE_KEY:
                    price=c.getInterstatePrice();
                    break;
                case Courier.INTERNATIONAL_COVERAGE_KEY:
                    price=c.getInternationalPrice();
                    break;
            }
            Address courierAddress=c.getAddress();
            double rating=c.getRating()/(double)c.getRatingCount();
            RCourier rCourier=new RCourier(c.getId(), c.getName(), c.getDescription(), rating, new Price(price, "$"),
                    addressService.addressStringWithoutPhoneAndName(courierAddress));

            rCouriers.add(rCourier);

        }*//*


        return rCouriers;
    }

}*/
