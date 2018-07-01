/*
package com.neo.Controller;

import com.neo.Constants;
import com.neo.DAO.*;
import com.neo.DatabaseModel.*;
import com.neo.DatabaseModel.Order.DesignerOrder;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Designer;
import com.neo.DatabaseModel.Users.User;
import com.neo.PayPalHelper;
import com.neo.Model.*;
import com.neo.Service.ImageService;
import com.neo.Utils.GeneralHelper;
import com.neo.Utils.JWTHelper;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

*/
/**
 * Created by localadmin on 14/6/17.
 *//*


@RestController
@CrossOrigin(Constants.FRONT_END)
public class DesignerOrderController {

    private final ImageService imageService;
    private final BASE64Decoder decoder;
    private DesignerOfferDAO designerOfferDAO;
    private DesignerBidDAO designerBidDAO;
    private final UserDAO userDAO;
    private final DesignerDAO designerDAO;
    private final DesignerOrderDAO designerOrderDAO;
    private final PaymentOrderDAO paymentOrderDAO;
    private final CustomerDAO customerDAO;
    private final CategoryDAO categoryDAO;
    private final PayPalHelper payPalHelper;
    private final SubCategoryDAO subCategoryDAO;
    private final NotificationDAO notificationDAO;
    private final CommissionDAO commissionDAO;

    @Autowired
    public DesignerOrderController(ImageService imageService, DesignerOfferDAO designerOfferDAO,
                                   DesignerBidDAO designerBidDAO, UserDAO userDAO,
                                   DesignerDAO designerDAO, DesignerOrderDAO designerOrderDAO,
                                   PaymentOrderDAO paymentOrderDAO, CustomerDAO customerDAO,
                                   CategoryDAO categoryDAO, PayPalHelper payPalHelper,
                                   SubCategoryDAO subCategoryDAO, NotificationDAO notificationDAO,
                                   CommissionDAO commissionDAO) {
        this.imageService = imageService;
        this.designerOfferDAO = designerOfferDAO;
        this.designerBidDAO = designerBidDAO;
        this.userDAO = userDAO;
        this.designerDAO = designerDAO;
        this.designerOrderDAO = designerOrderDAO;
        this.paymentOrderDAO = paymentOrderDAO;
        this.customerDAO = customerDAO;
        this.categoryDAO = categoryDAO;
        this.payPalHelper = payPalHelper;
        this.subCategoryDAO = subCategoryDAO;
        this.notificationDAO = notificationDAO;
        this.commissionDAO = commissionDAO;
        decoder = new BASE64Decoder();
    }

    @RequestMapping("/getDesignerOffers")
    public ArrayList<RDesignerOffer> getDesignerOffers(@RequestHeader("Authorization") String token) throws SQLException {
        Designer designer = designerDAO.get(JWTHelper.decodeToken(token).getId());
        if (designer == null) return null;

        ArrayList<RDesignerOffer> rDesignerOffers = new ArrayList<>();

        List<DesignerOffer> offers = designerOfferDAO.listAll();
        for (DesignerOffer offer : offers) {

            boolean b = false;
            List<DesignerBid> bids = offer.getBids();
            for (DesignerBid bid : bids)
                if (bid.getDesigner().getId().equals(designer.getId())) {
                    b = true;
                    break;
                }
            if (b) continue;
            RDesignerOffer rDesignerOffer = new RDesignerOffer(offer.getId(), offer.getDescription(),
                    offer.getCategory().getName(), offer.getSubCategory().getName(), offer.getCustomer().getId());
            rDesignerOffers.add(rDesignerOffer);
        }

        return rDesignerOffers;

    }

    @RequestMapping("/getMyDesignerOffers")
    public ArrayList<RDesignerOffer> getMyDesignerOffers(@RequestHeader("Authorization") String token) throws SQLException {
        Customer customer = customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer == null) return new ArrayList<>();

        ArrayList<RDesignerOffer> rDesignerOffers = new ArrayList<>();

        List<DesignerOffer> offers = customer.getOffers();
        for (DesignerOffer offer : offers) {
            //if (offer.getDesignerOrder() != null) continue;
            RDesignerOffer rDesignerOffer = new RDesignerOffer(offer.getId(), offer.getDescription(),
                    offer.getCategory().getName(), offer.getSubCategory().getName());
            rDesignerOffers.add(rDesignerOffer);
        }

        return rDesignerOffers;

    }

    @RequestMapping("/getDesignerOrders")
    public ArrayList<RDesignerOrder> getDesignerOrders(@RequestHeader("Authorization") String token) throws SQLException {
        ArrayList<RDesignerOrder> rDesignerOrders = new ArrayList<>();

        Designer designer = designerDAO.get(JWTHelper.decodeToken(token).getId());
        if (designer == null) return rDesignerOrders;

        List<DesignerOrder> designerOrders = designer.getOrders();

        for (DesignerOrder order : designerOrders) {

            DesignerOffer offer = order.getDesignerOffer();
            DesignerBid bid = order.getBid();
            String status=DesignerOrder.statusMap.get(order.getStatus());
            RDesignerOrder rDesignerOrder = new RDesignerOrder(new RDesignerBid(bid.getId(), bid.getAmt(),
                    Integer.parseInt(bid.getDuration()), order.getDesigner().getId()), new RDesignerOffer(
                    offer.getId(), offer.getDescription(), offer.getCategory().getName(),
                    offer.getSubCategory().getName(), order.getCustomer().getId()),
                    order.getId(), status, order.getImg(), designerOrderDAO.canDesignerCancel(order), designerOrderDAO.canComplain(order),
                    designerOrderDAO.canContact(order), designerOrderDAO.canChangeStatus(order), designerOrderDAO.canReview(order));

            rDesignerOrders.add(rDesignerOrder);
        }
        return rDesignerOrders;
    }



    @RequestMapping("/getMyDesignerBids")
    public ArrayList<RDesignerBid> getMyDesignerBids(@RequestHeader("Authorization") String token, @RequestParam Long ofid) throws SQLException {
        ArrayList<RDesignerBid> rDesignerBids = new ArrayList<>();

        Customer customer = customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer == null) return rDesignerBids;

        DesignerOffer offer = designerOfferDAO.get(ofid);
        if (!offer.getCustomer().getId().equals(customer.getId())) return rDesignerBids;

        List<DesignerBid> bids = offer.getBids();
        for (DesignerBid bid : bids) {
            RDesignerBid rDesignerBid = new RDesignerBid(bid.getId(), bid.getAmt(),
                    Integer.parseInt(bid.getDuration()), bid.getDesigner().getId());
            rDesignerBids.add(rDesignerBid);
        }

        return rDesignerBids;

    }



    @RequestMapping("/deleteDesignerOffer")
    public boolean deleteDesignerOffer(@RequestParam Long ofid, @RequestHeader("Authorization") String token) throws SQLException {
        Customer customer = customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer == null) return false;

        DesignerOffer offer = designerOfferDAO.get(ofid);
        if (offer == null || !offer.getCustomer().getId().equals(customer.getId())) return false;

        designerOfferDAO.delete(offer);
        return true;
    }

    @RequestMapping("/cancelDesignerOrder")
    public boolean cancelDesignerOrder(@RequestParam Long id, @RequestHeader("Authorization") String token) throws SQLException {
        User u = userDAO.get(JWTHelper.decodeToken(token).getId());

        DesignerOrder order = designerOrderDAO.get(id);
        if (order == null || order.getStatus()==DesignerOrder.STATUS_CANCELED_KEY) return false;

        switch (u.getType()) {
            case User.DESIGNER_TYPE:
                if (!designerOrderDAO.canDesignerCancel(order, designerDAO.get(u.getId()))) return false;
                else if (payPalHelper.refundPayment(order.getPaymentOrder().getSaleId(),
                        order.getPrice())) {
                    notificationDAO.addNotification(Notification.TYPE_CUSTOMER_DESIGNER_ORDER,
                            Notification.MESSAGE_ORDER_CANCELED, order.getCustomer());
                } else return false;
                break;
            case User.CUSTOMER_TYPE:
                if (!designerOrderDAO.canCustomerCancel(order, customerDAO.get(u.getId()))) return false;
                else {
                    if (payPalHelper.refundPayment(order.getPaymentOrder().getSaleId(),
                            order.getPrice())) {
                        notificationDAO.addNotification(Notification.TYPE_DESIGNER_ORDER,
                                Notification.MESSAGE_ORDER_CANCELED, order.getDesigner());
                    } else return false;
                }
                break;
            default:
                return false;
        }

        order.setStatus(DesignerOrder.STATUS_CANCELED_KEY);
        designerOrderDAO.update(order);
        return true;
    }

    @RequestMapping(value = "/acceptDesignerOffer", method = RequestMethod.POST)
    public boolean acceptDesignerOffer(@RequestBody RDesignerOrder rDesignerOrder, @RequestHeader("Authorization") String token) throws SQLException, PayPalRESTException {

        Customer customer = customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer == null) return false;

        DesignerBid bid = designerBidDAO.get(rDesignerOrder.getBid().getId());
        if(bid==null) return false;
        DesignerOffer offer = bid.getOffer();
        if(offer==null) return false;

        String saleId = payPalHelper.executePayment(rDesignerOrder.getPayerID(), rDesignerOrder.getPaymentID(), 1111111);
        if (saleId == null) return false;
        PaymentOrder paymentOrder = new PaymentOrder(saleId);
        paymentOrderDAO.save(paymentOrder);

        DesignerOrder designerOrder = new DesignerOrder(bid.getAmt(), paymentOrder, offer.getCustomer(), bid.getDesigner());

        bid.setDesigner(null);
        bid.setOffer(null);
        designerBidDAO.update(bid);

        offer.setCustomer(null);
        designerOfferDAO.update(offer);

        designerOrder.setBid(bid);
        designerOrder.setDesignerOffer(offer);
        designerOrderDAO.save(designerOrder);

        notificationDAO.addNotification(Notification.TYPE_DESIGNER_ORDER,
                Notification.MESSAGE_NEW_ORDER, designerOrder.getDesigner());

        return true;
    }


    @RequestMapping("/getMyDesignerOrders")
    public ArrayList<RDesignerOrder> getMyDesignerOrders(@RequestHeader("Authorization") String token) throws SQLException {
        ArrayList<RDesignerOrder> rDesignerOrders = new ArrayList<>();

        Customer customer = customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer == null) return rDesignerOrders;

        List<DesignerOrder> designerOrders = customer.getDesignerOrders();

        for (DesignerOrder order : designerOrders) {

            DesignerOffer offer = order.getDesignerOffer();
            DesignerBid bid = order.getBid();
            String status=DesignerOrder.statusMap.get(order.getStatus());
            boolean canCancel=designerOrderDAO.canCustomerCancel(order);
            boolean canComplain=designerOrderDAO.canComplain(order);
            boolean canContact=designerOrderDAO.canContact(order);
            boolean canReview=designerOrderDAO.canReview(order);

            RDesignerOrder rDesignerOrder = new RDesignerOrder(new RDesignerBid(bid.getId(), bid.getAmt(),
                    Integer.parseInt(bid.getDuration()), order.getDesigner().getId()),
                    new RDesignerOffer(offer.getId(), offer.getDescription(), offer.getCategory().getName(),
                            offer.getSubCategory().getName()), order.getId(), status, order.getImg(), canCancel, canComplain, canContact,
                    false, canReview);

            rDesignerOrders.add(rDesignerOrder);
        }
        return rDesignerOrders;
    }

    */
/*}*//*


    */
/*@RequestMapping(value = "/NewDesignerOffer", method = RequestMethod.POST)
    public Long NewDesignerOffer(@RequestBody RDesignerOffer rDesignerOffer, @RequestHeader("Authorization") String token) throws SQLException, IOException {
        Customer customer = customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer == null) return null;

        DesignerOffer designerOffer = new DesignerOffer(customer, rDesignerOffer.getDescription(),
                categoryDAO.get(rDesignerOffer.getCat()), subCategoryDAO.get(rDesignerOffer.getCat()));
        designerOffer = designerOfferDAO.save(designerOffer);

        return designerOffer.getId();
    }*//*


    @RequestMapping(value = "/uploadDesign", method = RequestMethod.POST)
    public boolean uploadDesign(@RequestHeader(value = "Authorization") String token, @RequestBody RUploadDesignOffer rUploadDesignOffer) throws SQLException, IOException {
        Designer designer = designerDAO.get(JWTHelper.decodeToken(token).getId());
        if (designer == null) return false;

        DesignerOrder designerOrder = designerOrderDAO.get(rUploadDesignOffer.getOrderId());
        //designerOrder.setStatus(DesignerOrder.STATUS_DISPATCHED);
        if (updateStatus(designerOrder, designer, DesignerOrder.STATUS_COMPLETED_KEY)) {
            String file = designer.getId() + new Date().getTime() + ".png";
            imageService.writeCardImage(decoder.decodeBuffer(rUploadDesignOffer.getImage()), file);
            designerOrder.setImg(file);
            designerOrderDAO.update(designerOrder);
            return true;
        } else return false;
    }

    @RequestMapping("/updateDesignerOrderStatus")
    public boolean updateStatus(@RequestHeader(value = "Authorization") String token, int status, Long orderId) {
        Designer designer = designerDAO.get(JWTHelper.decodeToken(token).getId());
        if (designer == null) return false;

        DesignerOrder order = designerOrderDAO.get(orderId);
        return order != null && updateStatus(order, designer, status);

    }

    private boolean updateStatus(DesignerOrder order, Designer designer, int status) {
        if(!designerOrderDAO.canChangeStatus(order, designer)) return false;

        order.setStatus(status);
        switch (status) {
            case DesignerOrder.STATUS_COMPLETED_KEY:
                float commission = commissionDAO.get(Commission.DESIGNER_TYPE).getAmt();
                float amt = (1f - commission / 100f) * order.getPrice();
                if (!payPalHelper.executePayment(amt, designer.getPaypal())) return false;

                designerOrderDAO.update(order);
                notificationDAO.addNotification(Notification.TYPE_CUSTOMER_DESIGNER_ORDER,
                        Notification.MESSAGE_ORDER_DELIVERED, order.getCustomer());
                break;
        }

        designerOrderDAO.update(order);

        return true;
    }

    @RequestMapping("/getDesignerOrderStatusMap")
    public List<RStatus> getStatusStrings() {
        List<RStatus> statuses=new ArrayList<>();

        HashMap<Integer, String> map= DesignerOrder.statusMap;
        for(Map.Entry<Integer, String> entry: map.entrySet()){
            if(entry.getKey()==-1) continue;
            statuses.add(new RStatus(entry.getKey(), entry.getValue()));
        }

        return statuses;
    }
}
*/
