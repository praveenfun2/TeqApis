package com.neo.Controller.Customer;

import com.neo.DAO.*;
import com.neo.DatabaseModel.Card.CustomerCard;
import com.neo.DatabaseModel.CardImage;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RCard;
import com.neo.Model.RCardImage;
import com.neo.Service.ImageService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@RestController("customerCardController")
@RequestMapping("/customer/card")
public class CardsController {

    private final SellerDAO sellerDAO;
    private final CustomerDAO customerDAO;
    private final CustomerCardDAO customerCardDAO;
    private final CartItemDAO cartItemDAO;
    private final ImageService imageService;
    private final CardImageDAO cardImageDAO;

    @Autowired
    public CardsController(SessionFactory sessionFactory, SellerDAO sellerDAO, CategoryDAO categoryDAO, SubCategoryDAO subCategoryDAO,
                           CardsSubcatDAO cardsSubcatDAO, CardsPaperDAO cardsPaperDAO,
                           CardsFinishDAO cardsFinishDAO, PaperSellerDAO paperSellerDAO,
                           FinishSellerDAO finishSellerDAO, PromotionDAO promotionDAO, CustomerDAO customerDAO, SellerCardFrontDAO frontDAO,
                           SellerCardBackDAO backDAO, CustomerCardDAO customerCardDAO, CardDAO cardDAO, UserDAO userDAO, SellerCardDAO sellerCardDAO, CartItemDAO cartItemDAO, ImageService imageService, CardImageDAO cardImageDAO) {
        this.imageService = imageService;
        this.sellerDAO = sellerDAO;
        this.customerDAO = customerDAO;
        this.customerCardDAO = customerCardDAO;
        this.cartItemDAO = cartItemDAO;
        this.cardImageDAO = cardImageDAO;
    }

    /*@RequestMapping("/assignSeller")
    public void assignSeller(@RequestAttribute String id, @RequestParam String sid, Long cid, HttpServletResponse response) {

        Customer customer = customerDAO.get(id);
        CustomerCard userCard = customerCardDAO.get(cid);
        Seller seller;
        if (userCard == null || !userCard.getCustomer().getId().equals(customer.getId()) || (seller = sellerDAO.get(sid)) == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        userCard.setSeller(seller);
        userCard.setPrice(seller.getPrice());
        customerCardDAO.update(userCard);

    }*/

    /**
     * @api {get} /customer/card/remove Delete user card
     * @apiDescription Delete a card design uploaded by customer.
     * @apiGroup Cards
     * @apiParam {Long} cid card id.
     * @apiError (404) - Invalid id.
     * @apiError (403) - Forbidden.
     */
    @RequestMapping("/remove")
    public void removeCard(@RequestAttribute String id, @RequestParam long cid, HttpServletResponse response) throws SQLException {
        Customer customer = customerDAO.get(id);
        CustomerCard userCard = customerCardDAO.get(cid);

        if(userCard==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (!userCard.getCustomer().getId().equals(customer.getId()) || !cartItemDAO.isCardDeletable(userCard)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        //remove from customer card table
        customerCardDAO.delete(userCard);
        //delete card image online
        imageService.deleteCard(userCard.getImages().get(0).getFileName());
        //delete from cardimage table
        cardImageDAO.delete(userCard.getImages().get(0));
    }

    /**
     * @api {post} /customer/card/new New user card
     * @apiDescription Upload a new card design by customer.
     * @apiGroup Cards
     * @apiParamExample {json} Request:
     * {
     * "encodedImage": Base64 encoded image,
     * "landscape": True/false
     * }
     * @apiSuccessExample {json} Response:- {
     *     "id": Card Image id,
     *     "card":{
     *         "id": card id
     *     }
     * }
     * @apiError (400) - Invalid input data.
     * @apiError (503) - Image Service(S3) is not working.
     */

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public RCardImage NewUserCard(@RequestAttribute String id, @RequestBody RCard r, HttpServletResponse response) throws IOException, SQLException {
        Customer customer = customerDAO.get(id);

        String filename = customer.getId() + "_" + new Date().getTime() + ".png";
        BufferedImage image=imageService.readImage(r.getEncodedImage());
        if(image==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        boolean written =imageService.writeCardImage(imageService.scaleImage(image, r.isLandscape()), filename);
        if(written){
            CustomerCard card = new CustomerCard(r.isLandscape(), null, customer);
            customerCardDAO.save(card);
            CardImage cardImage=new CardImage(filename, card, null);
            cardImageDAO.save(cardImage);
            RCard result=new RCard();
            result.setId(card.getId());
            RCardImage rCardImage=new RCardImage();
            rCardImage.setId(cardImage.getId());
            rCardImage.setCard(result);
            return rCardImage;
        }else {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return null;
        }
    }
}
