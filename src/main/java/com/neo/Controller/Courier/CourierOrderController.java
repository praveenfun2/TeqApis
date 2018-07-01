/*
package com.neo.Controller.Courier;

import com.neo.DAO.*;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Commission;
import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.PaymentOrder;
import com.neo.DatabaseModel.Users.Courier;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.DatabaseModel.Users.User;
import com.neo.GeocodingHelper;
import com.neo.Model.Price;
import com.neo.Model.RCourierOrder;
import com.neo.Model.RStatus;
import com.neo.PayPalHelper;
import com.neo.Service.AddressService;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.neo.Controller.CourierOrderController.getStatus;
import static com.neo.DatabaseModel.Order.CourierOrder.*;

@RestController("cCourierOrderController")
@RequestMapping("/courier/order")
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

    @RequestMapping("")
    public List<RCourierOrder> getCourierOrders(@RequestHeader("Authorization") String token){
        Courier courier = courierDAO.get(JWTHelper.decodeToken(token).getId());
        if (courier == null) return null;

        ArrayList<RCourierOrder> rCourierOrders=new ArrayList<>();

        List<CourierOrder> courierOrders=courier.getOrders();
        for(CourierOrder co: courierOrders){
            RCourierOrder rCourierOrder=new RCourierOrder(co.getId(),getStatus(co.getStatus()),
                    addressService.addressString(co.getCardOrder().getAddress()), new Price(co.getPrice(), "$"), courierOrderDAO.canCourierCancel(co),
                    courierOrderDAO.canReview(co), courierOrderDAO.canComplain(co), courierOrderDAO.canContact(co), courierOrderDAO.canChangeStatus(co));
            rCourierOrders.add(rCourierOrder);
        }

        return rCourierOrders;
    }

    @RequestMapping("/updateCourierOrderStatus")
    public boolean UpdateOrderStatus(@RequestHeader("Authorization") String token, int status, Long orderId) throws SQLException {

        Courier courier = courierDAO.get(JWTHelper.decodeToken(token).getId());
        if (courier == null) return false;

        CourierOrder order =courierOrderDAO.get(orderId);
        if (order == null) return false;

        if(!courierOrderDAO.canChangeStatus(order, courier)) return false;

        CardOrder cardOrder=order.getCardOrder();
        if(cardOrder==null) return false;

        if (status == STATUS_DELIVERED_KEY) {
            float commission = commissionDAO.get(Commission.SELLER_TYPE).getAmt();
            float price = (1f - commission / 100f) * cardOrder.getPrice();
            if (!payPalHelper.executePayment(price, sellerDAO.get(cardOrder.getSeller().getId()).getPaypal())) return false;

            commission=commissionDAO.get(Commission.COURIER_TYPE).getAmt();
            price=(1f-commission/100f)*order.getPrice();
            if (!payPalHelper.executePayment(price, courierDAO.get(order.getCourier().getId()).getPaypal())) return false;
        }

        order.setStatus(status);
        courierOrderDAO.update(order);

        if(status<STATUS_PICKED_UP_KEY)
            cardOrder.setStatus(CardOrder.STATUS_PRINTED_KEY);
        else cardOrder.setStatus(status);
        cardOrderDAO.update(cardOrder);

        return true;

    }
    @RequestMapping("/cancelCourierOrder")
    public boolean cancelOrder(@RequestHeader("Authorization") String token, Long id) {
        User u = userDAO.get(JWTHelper.decodeToken(token).getId());

        CourierOrder order=courierOrderDAO.get(id);
        if(order==null) return false;

        switch (u.getType()){
            case User.SELLER_TYPE:
                if (!courierOrderDAO.canSellerCancel(order, sellerDAO.get(u.getId()))) return false;
                break;
            case User.COURIER_TYPE:
                if (!courierOrderDAO.canCourierCancel(order, courierDAO.get(u.getId()))) return false;
                break;
        }

        CardOrder cardOrder=order.getCardOrder();
        cardOrder.setCourierOrder(null);
        cardOrder.setStatus(CardOrder.STATUS_PRINTED_KEY);
        cardOrderDAO.update(cardOrder);

        order.setStatus(STATUS_CANCELED_KEY);
        courierOrderDAO.update(order);

        boolean refund = payPalHelper.refundPayment(order.getPaymentOrder().getSaleId(),
                order.getPrice());
        return refund;
    }
}*/
