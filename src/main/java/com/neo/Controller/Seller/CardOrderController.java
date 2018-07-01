/*
package com.neo.Controller.Seller;

import com.neo.Constants;
import com.neo.Controller.CourierOrderController;
import com.neo.DAO.*;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Notification;
import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.Users.Courier;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.*;
import com.neo.PayPalHelper;
import com.neo.Service.AddressService;
import com.neo.Service.ImageService;
import com.neo.Utils.DateTime;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.neo.Controller.CardOrderController.getStatus;
import static com.neo.DatabaseModel.Order.CardOrder.*;

@RestController("sellerCardOrderController")
@RequestMapping("/seller/order/card")
public class CardOrderController {

    private final CartItemDAO cartItemDAO;
    private final CardOrderDAO cardOrderDAO;
    private final PaymentOrderDAO paymentOrderDAO;
    private final SellerDAO sellerDAO;
    private final CustomerDAO customerDAO;
    private final UserDAO userDAO;
    private final NotificationDAO notificationDAO;
    private final PayPalHelper payPalHelper;
    private final CourierOrderDAO courierOrderDAO;
    private final CustomerCardDAO customerCardDAO;
    private final ImageService imageService;
    private final OrderDAO orderDAO;
    private final AddressDAO addressDAO;
    private final SellerCardDAO sellerCardDAO;
    private final AddressService addressService;
    private final DateTime dateTime;

    @Autowired
    public CardOrderController(AddressDAO addressDAO, CartItemDAO cartItemDAO, CardOrderDAO cardOrderDAO, PaymentOrderDAO paymentOrderDAO,
                               SellerDAO sellerDAO, CustomerDAO customerDAO, UserDAO userDAO, NotificationDAO notificationDAO,
                               PayPalHelper payPalHelper, CourierOrderDAO courierOrderDAO, CustomerCardDAO customerCardDAO,
                               ImageService imageService, OrderDAO orderDAO, AddressDAO addressDAO1, SellerCardDAO sellerCardDAO,
                               AddressService addressService, DateTime dateTime) {
        this.addressDAO = addressDAO1;
        this.addressService = addressService;
        this.cartItemDAO = cartItemDAO;
        this.cardOrderDAO = cardOrderDAO;
        this.paymentOrderDAO = paymentOrderDAO;
        this.sellerDAO = sellerDAO;
        this.customerDAO = customerDAO;
        this.userDAO = userDAO;
        this.notificationDAO = notificationDAO;
        this.payPalHelper = payPalHelper;
        this.courierOrderDAO = courierOrderDAO;
        this.customerCardDAO = customerCardDAO;
        this.imageService = imageService;
        this.orderDAO = orderDAO;
        this.sellerCardDAO = sellerCardDAO;
        this.dateTime = dateTime;
    }

    public static final String STATUS_PRINTED = "printed", STATUS_RECEIVED = "received",
            STATUS_PROGRESS = "In progress", STATUS_CANCELED = "canceled", STATUS_DISPATCHED = "dispatched";

    @RequestMapping("/status/update")
    public void UpdateOrderStatus(int status, Long orderId, @RequestAttribute String id, HttpServletResponse response) throws SQLException {

        Seller seller = sellerDAO.get(id);

        CardOrder cardOrder;
        if (!cardOrderDAO.canChangeStatus(cardOrder = cardOrderDAO.get(orderId), seller)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        cardOrder.setStatus(status);
        cardOrderDAO.update(cardOrder);

        notificationDAO.addNotification(Notification.TYPE_CUSTOMER_CARD_ORDER,
                Notification.MESSAGE_ORDER_UPDATE, cardOrder.getCustomer());

    }

    @RequestMapping("")
    public ArrayList<RCardOrder> getSellerOrder(@RequestAttribute String id) throws SQLException {
        ArrayList<RCardOrder> rCardOrders = new ArrayList<>();

        Seller seller = sellerDAO.get(id);

        List<CardOrder> cardOrders = seller.getOrders();
        for (CardOrder cardOrder : cardOrders) {
            CourierOrder courierOrder = cardOrder.getCourierOrder();

            String status = getStatus(cardOrder.getStatus());

            RCardOrder rCardOrder = new RCardOrder(addressService.addressString(cardOrder.getAddress()), status, cardOrder.getfCardImage(),
                    cardOrder.getbCardImage(), dateTime.getFormatedTime(cardOrder.getTimestamp()), dateTime.getFormatedTime(cardOrder.getsTimestamp()),
                    cardOrder.getPrice(), cardOrder.getId(), cardOrderDAO.canSellerCancel(cardOrder),
                    cardOrderDAO.canComplain(cardOrder), cardOrderDAO.canContact(cardOrder), cardOrderDAO.canChangeStatus(cardOrder),
                    cardOrderDAO.canReview(courierOrder));

            Customer customer = cardOrder.getCustomer();
            rCardOrder.setCustomer(new RUser(customer.getId(), customer.getName()));

            RCourierOrder rRCourierOrder;
            if (courierOrder == null)
                rRCourierOrder = new RCourierOrder(cardOrder.getStatus() != STATUS_CANCELED_KEY, false);
            else {
                Courier courier = courierOrder.getCourier();
                rRCourierOrder = new RCourierOrder(courierOrder.getId(), courier.getName(), new Price(courierOrder.getPrice(), "$")
                        , false, courierOrderDAO.canSellerCancel(courierOrder), courierOrderDAO.canReview(courierOrder),
                        courierOrderDAO.canComplain(courierOrder), courierOrderDAO.canContact(courierOrder));
                rRCourierOrder.setBooked(true);
            }
            rCardOrder.setCourierOrder(rRCourierOrder);

            rCardOrders.add(rCardOrder);
        }

        return rCardOrders;
    }

    @RequestMapping("/cancel")
    public void cancelOrder(@RequestParam Long orderId, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Seller seller = sellerDAO.get(id);

        CardOrder order = cardOrderDAO.get(orderId);
        if (order == null || !cardOrderDAO.canSellerCancel(order, seller)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int ts = order.getStatus();
        order.setStatus(STATUS_CANCELED_KEY);
        cardOrderDAO.update(order);

        boolean refund = payPalHelper.refundPayment(order.getPaymentOrder().getSaleId(),
                order.getPrice());
        if (!refund) {
            order.setStatus(ts);
            cardOrderDAO.update(order);
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }

        notificationDAO.addNotification(Notification.TYPE_CUSTOMER_CARD_ORDER,
                Notification.MESSAGE_ORDER_CANCELED, order.getSeller());
    }



}
*/
