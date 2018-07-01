/*
package com.neo.Controller.Customer;

import com.neo.CardImage;
import com.neo.Constants;
import com.neo.Controller.CourierOrderController;
import com.neo.DAO.*;
import com.neo.DatabaseModel.*;
import com.neo.DatabaseModel.Card.Card;
import com.neo.DatabaseModel.Card.CustomerCard;
import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RCardOrder;
import com.neo.Model.RUser;
import com.neo.PayPalHelper;
import com.neo.Service.AddressService;
import com.neo.Service.ImageService;
import com.neo.Utils.DateTime;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.neo.Controller.CardOrderController.getStatus;
import static com.neo.DatabaseModel.Order.CardOrder.*;
import static com.neo.Service.ImageService.CARDS;
import static com.neo.Utils.GeneralHelper.isNotEmpty;

@RestController("customerCardOrderController")
@RequestMapping("/customer/order/card")
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
*/
/*

    public static final String STATUS_PRINTED = "printed", STATUS_RECEIVED = "received",
            STATUS_PROGRESS = "In progress", STATUS_CANCELED = "canceled", STATUS_DISPATCHED = "dispatched";
*//*


    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public String executeOrder(@RequestBody RCardOrder rOrder, @RequestAttribute String id, HttpServletResponse response)
            throws SQLException, PayPalRESTException {
        Customer customer = customerDAO.get(id);

        Address address = addressDAO.get(rOrder.getAddressId());
        if (address == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        String saleId = payPalHelper.executePayment(rOrder.getPayerID(), rOrder.getPaymentID(),
                cartItemDAO.getTotalCartPrice(customer.getCartItems()));
        if (saleId == null) {
            response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
            return null;
        }
        PaymentOrder paymentOrder = new PaymentOrder(saleId);
        paymentOrderDAO.save(paymentOrder);

        address.setId(null);
        address.setCustomer(null);
        addressDAO.save(address);

        List<CartItem> cartItems = customer.getCartItems();
        for (CartItem cartItem : cartItems) {
            Item item = cartItem.getfItem();

            RCardImage.Builder builder = new RCardImage.Builder()
                    .setFile(new File(CARDS, item.getBgimg()));
            for(TextBox t: item.getTextBoxes())
                builder.addComponent(t.getContent(), t.getX(), t.getY(), t.getW(), t.getH());
            if (isNotEmpty(item.getLogo()))
                builder.addImageComponent(new File(ImageService.LOGOS, item.getLogo()), item.getLx(), item.getLy(), item.getLw(), item.getLh());

            RCardImage cardImage = builder.build();

            String filename = new Date().getTime() + ".png";
            imageService.saveCard(filename, cardImage);
            imageService.deleteLogo(item.getLogo());

            Card card = item.getCard();
            Seller seller = null;
            switch (card.getType()) {
                case Card.CUSTOMER_CARD:
                    CustomerCard customerCard = customerCardDAO.get(card.getId());
                    seller = customerCard.getSeller();
                    break;
                case Card.SELLER_CARD_FRONT:
                case Card.SELLER_CARD_BACK:
                    seller = sellerCardDAO.get(card.getId()).getSeller();
                    break;
            }

            CardOrder cardOrder = new CardOrder(cartItem.getQuantity(), cartItemDAO.getCartItemPrice(cartItem), address,
                    paymentOrder, seller, filename, cartItem.getFinishSeller(), cartItem.getPaperSeller(), customer);

            Item bItem = cartItem.getbItem();
            if (bItem != null) {
                builder = new RCardImage.Builder()
                        .setFile(new File(CARDS, bItem.getBgimg()));
                if (isNotEmpty(bItem.getLogo()))
                    builder.addImageComponent(new File(ImageService.LOGOS, bItem.getLogo()), bItem.getLx(), bItem.getLy(), bItem.getLw(), bItem.getLh());
                if (isNotEmpty(bItem.getQr()))
                    builder.setQrComponent(bItem.getQr(), bItem.getQrs(), bItem.getQry(), bItem.getQrw(), bItem.getQrh());
                cardImage = builder.build();

                filename = new Date().getTime() + ".png";
                imageService.saveCard(filename, cardImage);
                imageService.deleteLogo(bItem.getLogo());
                cardOrder.setbCardImage(filename);
            }
            cardOrderDAO.save(cardOrder);
            cartItemDAO.delete(cartItem);

            notificationDAO.addNotification(Notification.TYPE_SELLER_CARD_ORDER,
                    Notification.MESSAGE_NEW_ORDER, seller);
        }

        notificationDAO.addNotification(Notification.TYPE_CUSTOMER_CARD_ORDER,
                Notification.MESSAGE_NEW_ORDER, customer);


        return saleId;

    }

    @RequestMapping("/customer")
    public ArrayList<RCardOrder> getCardOrders(@RequestAttribute String id) throws SQLException {
        ArrayList<RCardOrder> RCardOrders = new ArrayList<>();

        Customer customer = customerDAO.get(id);

        List<CardOrder> cardOrders = customer.getCardOrders();
        for (CardOrder cardOrder : cardOrders) {
            String status = getStatus(cardOrder.getStatus());

            RCardOrder RCardOrder = new RCardOrder(addressService.addressString(cardOrder.getAddress()), status, cardOrder.getfCardImage(),
                    cardOrder.getbCardImage(), dateTime.getFormatedTime(cardOrder.getTimestamp()), dateTime.getFormatedTime(cardOrder.getsTimestamp()),
                    cardOrder.getPrice(), cardOrder.getId(), cardOrderDAO.canCustomerCancel(cardOrder),
                    cardOrderDAO.canComplain(cardOrder), cardOrderDAO.canContact(cardOrder), false,
                    cardOrderDAO.canReview(cardOrder.getCourierOrder()));
            Seller seller = cardOrder.getSeller();
            RCardOrder.setSeller(new RUser(seller.getId(), seller.getName()));
            RCardOrders.add(RCardOrder);
        }

        return RCardOrders;
    }

    @RequestMapping("/cancel")
    public void cancelOrder(@RequestParam Long orderId, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Customer customer = customerDAO.get(id);

        CardOrder order = cardOrderDAO.get(orderId);
        if (order == null || !cardOrderDAO.canCustomerCancel(order, customer)) {
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

        notificationDAO.addNotification(Notification.TYPE_SELLER_CARD_ORDER,
                Notification.MESSAGE_ORDER_CANCELED, order.getCustomer());

    }

}
*/
