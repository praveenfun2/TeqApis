/*
package com.neo.Controller;


import com.neo.Constants;
import com.neo.DAO.*;
import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.Order.DesignerOrder;
import com.neo.DatabaseModel.Rating;
import com.neo.DatabaseModel.Users.Courier;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Designer;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RRating;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(Constants.FRONT_END)
public class RatingController {

    private final RatingDAO ratingDAO;
    private final CustomerDAO customerDAO;
    private final DesignerDAO designerDAO;
    private final SellerDAO sellerDAO;
    private final CourierDAO courierDAO;
    private final CardOrderDAO cardOrderDAO;
    private final CourierOrderDAO courierOrderDAO;
    private final DesignerOrderDAO designerOrderDAO;

    @Autowired
    public RatingController(RatingDAO ratingDAO, CustomerDAO customerDAO, DesignerDAO designerDAO, CardOrderDAO cardOrderDAO, CourierOrderDAO courierOrderDAO, DesignerOrderDAO designerOrderDAO, SellerDAO sellerDAO, CourierDAO courierDAO) {
        this.ratingDAO = ratingDAO;
        this.customerDAO = customerDAO;
        this.designerDAO = designerDAO;
        this.cardOrderDAO = cardOrderDAO;
        this.courierOrderDAO = courierOrderDAO;
        this.designerOrderDAO = designerOrderDAO;
        this.sellerDAO = sellerDAO;
        this.courierDAO = courierDAO;
    }

    @RequestMapping("reviewSeller")
    public boolean reviewSeller(@RequestHeader("Authorization") String token, @RequestBody RRating rRating){
        Customer customer = customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer == null) return false;

        CardOrder order=cardOrderDAO.get(rRating.getOrderId());
        if(order==null) return false;

        Seller seller=order.getSeller();

        Rating rating=ratingDAO.rateUser(rRating.getRating(), rRating.getReview(), seller, order.getSellerRating());
        if(rating==null) return false;
        order.setSellerRating(rating);

        cardOrderDAO.update(order);
        sellerDAO.update(seller);

        return true;
    }

    @RequestMapping("reviewCustomer")
    public boolean reviewCustomer(@RequestHeader("Authorization") String token, @RequestBody RRating rRating){
        Seller seller = sellerDAO.get(JWTHelper.decodeToken(token).getId());
        if (seller == null) return false;

        CardOrder order=cardOrderDAO.get(rRating.getOrderId());
        if(order==null) return false;

        Customer customer=order.getCustomer();

        Rating rating=ratingDAO.rateUser(rRating.getRating(), rRating.getReview(), customer, order.getCustomerRating());
        if(rating==null) return false;
        order.setCustomerRating(rating);

        cardOrderDAO.update(order);
        customerDAO.update(customer);

        return true;
    }

    @RequestMapping("reviewCourier")
    public boolean reviewCourier(@RequestHeader("Authorization") String token, @RequestBody RRating rRating){
        Seller seller = sellerDAO.get(JWTHelper.decodeToken(token).getId());
        if (seller == null) return false;

        CourierOrder order=courierOrderDAO.get(rRating.getOrderId());
        if(order==null) return false;

        Courier courier=order.getCourier();

        Rating rating=ratingDAO.rateUser(rRating.getRating(), rRating.getReview(), courier, order.getCourierRating());
        if(rating==null) return false;
        order.setCourierRating(rating);

        courierOrderDAO.update(order);
        courierDAO.update(courier);

        return true;
    }

    @RequestMapping("reviewDesigner")
    public boolean reviewDesigner(@RequestHeader("Authorization") String token, @RequestBody RRating rRating){
        Customer customer = customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer == null) return false;

        DesignerOrder order=designerOrderDAO.get(rRating.getOrderId());
        if(order==null) return false;

        Designer designer=order.getDesigner();

        Rating rating=ratingDAO.rateUser(rRating.getRating(), rRating.getReview(), designer, order.getDesignerRating());
        if(rating==null) return false;
        order.setDesignerRating(rating);

        designerOrderDAO.update(order);
        designerDAO.update(designer);

        return true;
    }
}
*/
