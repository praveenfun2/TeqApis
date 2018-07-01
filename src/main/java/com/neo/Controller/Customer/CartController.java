package com.neo.Controller.Customer;

import com.neo.DAO.*;
import com.neo.DatabaseModel.Card.Card;
import com.neo.DatabaseModel.CartItem;
import com.neo.DatabaseModel.Item;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RCartItem;
import com.neo.Model.RItem;
import com.neo.Service.ImageService;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

@RestController
@RequestMapping("/customer/cart")
public class CartController {

    private final ItemDAO itemDAO;
    private final CardDAO cardDAO;
    private final CartItemDAO cartItemDAO;
    private final FinishSellerDAO finishSellerDAO;
    private final PaperSellerDAO paperSellerDAO;
    private final CustomerDAO customerDAO;
    private final SellerCardBackDAO backDAO;
    private final SellerCardFrontDAO frontDAO;
    private final CustomerCardDAO customerCardDAO;
    private final SellerCardDAO sellerCardDAO;
    private final CardImageDAO cardImageDAO;
    private final SellerDAO sellerDAO;
    private final ImageService imageService;

    @Autowired
    public CartController(ItemDAO itemDAO, CardDAO cardDAO, CartItemDAO cartItemDAO, FinishSellerDAO finishSellerDAO, PaperSellerDAO paperSellerDAO,
                          CustomerDAO customerDAO, SellerCardBackDAO backDAO, SellerCardFrontDAO frontDAO, CustomerCardDAO customerCardDAO,
                          SellerCardDAO sellerCardDAO, CardImageDAO cardImageDAO, SellerDAO sellerDAO, ImageService imageService) {
        this.itemDAO = itemDAO;
        this.cardDAO = cardDAO;
        this.cartItemDAO = cartItemDAO;
        this.finishSellerDAO = finishSellerDAO;
        this.paperSellerDAO = paperSellerDAO;
        this.customerDAO = customerDAO;
        this.backDAO = backDAO;
        this.frontDAO = frontDAO;
        this.customerCardDAO = customerCardDAO;
        this.sellerCardDAO = sellerCardDAO;
        this.cardImageDAO = cardImageDAO;
        this.sellerDAO = sellerDAO;
        this.imageService = imageService;
    }

    /*@RequestMapping("")
    public ArrayList<RCartItem> getCards(@RequestAttribute String id) throws SQLException {
        ArrayList<RCartItem> rItemDetails = new ArrayList<>();

        Customer customer = customerDAO.get(id);

        List<CartItem> c = customer.getCartItems();

        for (CartItem cartItem : c) {
            RCartItem rCartItem = new RCartItem(cartItem.getId());

            Item item = cartItem.getfItem();
            RItem rItem = new RItem();
            itemDAO.buildItemResponse(item, rItem);
            rCartItem.setfItem(rItem);

            Item bItem = cartItem.getbItem();
            RItem bRItem = new RItem();
            itemDAO.buildItemResponse(bItem, bRItem);
            rCartItem.setbItem(bRItem);

            switch (item.getCard().getType()) {
                case Card.CUSTOMER_CARD:
                    CustomerCard customerCard = customerCardDAO.get(item.getCard().getId());

                    Seller seller = customerCard.getSeller();

                    List<PaperSeller> paperSellers = seller.getPapers();
                    for (PaperSeller pS : paperSellers)
                        rCartItem.addPaper(pS.getPaper().getName(), pS.getId(), pS.getPrice());

                    List<FinishSeller> finishSellers = seller.getFinishes();

                    for (FinishSeller fS : finishSellers)
                        rCartItem.addFinish(fS.getFinish().getFname(), fS.getId(), fS.getPrice());

                    break;

                case Card.SELLER_CARD_FRONT:
                    SellerCardFront sellerCardFront = frontDAO.get(item.getCard().getId());

                    List<CardsPaper> cardsPapers = sellerCardFront.getPapers();

                    for (CardsPaper cP : cardsPapers) {
                        PaperSeller pS = cP.getPaper();
                        rCartItem.addPaper(pS.getPaper().getName(), pS.getId(), pS.getPrice());
                    }

                    List<CardsFinish> cardsFinishes = sellerCardFront.getFinishes();
                    for (CardsFinish cF : cardsFinishes) {
                        FinishSeller fS = cF.getFinish();
                        rCartItem.addFinish(fS.getFinish().getFname(), fS.getId(), fS.getPrice());
                    }

                    break;
            }
            rItemDetails.add(rCartItem);
        }

        return rItemDetails;
    }*/

    /**
     * @api {get} /customer/cart/remove Remove cartItem.
     * @apiDescription Remove a card design from cart.
     * @apiGroup Cart
     * @apiParam {Long} cartItemId CartItem's id to remove.
     * @apiError (400) - Invalid input data.
     * @apiError (503) - Image Service(S3) is not working.
     */
    @RequestMapping("/remove")
    public void removeItem(@RequestAttribute String id, @RequestParam Long cartItemId, HttpServletResponse response) {
        Customer customer = customerDAO.get(id);

        CartItem cartItem = cartItemDAO.get(cartItemId);
        if (cartItem == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else if (!cartItem.getCustomer().getId().equals(customer.getId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Item fItem = cartItem.getfItem();
        if (isNotEmpty(fItem.getLogo()))
            if (!imageService.deleteLOGO(fItem.getLogo())) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return;
            }

        Item bItem = cartItem.getbItem();
        if (bItem != null && isNotEmpty(bItem.getLogo()))
            if (!imageService.deleteLOGO(bItem.getLogo())) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return;
            }

        cartItemDAO.delete(cartItem);
    }

    /**
     * @api {post} /customer/cart/add New cart item
     * @apiDescription Add a new card design to cart.
     * @apiGroup Cart
     * @apiParamExample {json} Request:
     * {
     * "sellerId": "Id of the printing company to which this card design is assigned if customer is using a custom card.",
     * "fItem":"details of front card design" {
     * "customCard" : "True if customer uses custom image as card image"
     * "image":{"id":"Background image id"},
     * "textBoxes":[{
     * "content":"content of the text box",
     * "x":"x - coordinate w.r.t to the card's top-left corner in % of card's width",
     * "y":"y - coordinate w.r.t to the card's top-left corner in % of card's height",
     * "w":"textBox's width w.r.t to the card's width in %",
     * "h":"textBox's height w.r.t to the card's width in % height"
     * }],
     * //logo is optional
     * "logo":"base64 encoded logo image file",
     * "lx":"logo's x coordinate",
     * "ly":"logo's y coordinate",
     * "lw":"logo's width",
     * "lh":"logo's height"
     * },
     * "bItem":"details of back card design" {
     * "image":{"id":"Background image id"},
     * "textBoxes":[{
     * "content":"content of the text box",
     * "x":"x - coordinate w.r.t to the card's top-left corner in % of card's width",
     * "y":"y - coordinate w.r.t to the card's top-left corner in % of card's height",
     * "w":"textBox's width w.r.t to the card's width in %",
     * "h":"textBox's height w.r.t to the card's width in % height"
     * }],
     * //logo is optional
     * "logo":"base64 encoded logo image file",
     * "lx":"logo's x coordinate",
     * "ly":"logo's y coordinate",
     * "lw":"logo's width",
     * "lh":"logo's height",
     * //qr is optional
     * "qr":"qr content",
     * "qrs":"qr image size in % of card's width"
     * }
     * }
     * @apiError (400) - Invalid input data.
     * @apiError (503) - Image Service(S3) is not working.
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Long AddToCart(@RequestAttribute String id, @RequestBody RCartItem ci, HttpServletResponse response) throws SQLException, IOException {
        Customer customer = customerDAO.get(id);

        RItem fRItem = ci.getfItem();
        RItem bRItem = ci.getbItem();
        if (fRItem == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Item fItem = new Item();
        itemDAO.updateItem(fItem, fRItem);
        fItem.setImage(cardImageDAO.get(fRItem.getImage().getId()));
        if (!itemDAO.isValid(fItem)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        Card card = fItem.getImage().getCard();
        if (card.getType() == Card.CUSTOMER_CARD) {
            Seller seller = sellerDAO.get(ci.getSellerId());
            if (seller == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }
            card.setSeller(seller);
            cardDAO.update(card);
        }

        Item bItem = null;
        if (bRItem != null) {
            bItem = new Item();
            itemDAO.updateItem(bItem, bRItem);
            bItem.setImage(cardImageDAO.get(bRItem.getImage().getId()));

            if (!itemDAO.isValid(bItem) || !bItem.getImage().getCard().getSeller().getId().
                    equals(fItem.getImage().getCard().getSeller().getId()) || bItem.getImage().getCard().getType() == Card.CUSTOMER_CARD) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }
        }


        if (isNotEmpty(fRItem.getLogo())) {
            String filename = customer.getId() + "f" + new Date().getTime() + ".png";
            if (!imageService.writeLogoImage(fRItem.getLogo(), filename)) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return null;
            }
        }
        if (bRItem != null && isNotEmpty(bRItem.getLogo())) {
            String filename = customer.getId() + "b" + new Date().getTime() + ".png";
            if (!imageService.writeLogoImage(bRItem.getLogo(), filename)) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return null;
            }
        }
        CartItem cartItem = new CartItem(fItem, bItem, customer);
        cartItemDAO.save(cartItem);

        return cartItem.getId();
    }

    /**
     * @api {post} /customer/cart/update Update cart item
     * @apiDescription Update a card design from cart.
     * @apiGroup Cart
     * @apiParamExample {json} Request:
     * {
     * "id":"CartItem id",
     * //All below parameters are optional. Only the provided parameters will be updated.
     * "fItem":"details of front card design" {
     * "textBoxes":[{
     * "content":"content of the text box",
     * "image":{"id":"Background image id"},
     * "x":"x - coordinate w.r.t to the card's top-left corner in % of card's width",
     * "y":"y - coordinate w.r.t to the card's top-left corner in % of card's height",
     * "w":"textBox's width w.r.t to the card's width in %",
     * "h":"textBox's height w.r.t to the card's width in % height"
     * }],
     * //logo is optional
     * "logo":"base64 encoded logo image file",
     * "lx":"logo's x coordinate",
     * "ly":"logo's y coordinate",
     * "lw":"logo's width",
     * "lh":"logo's height"
     * },
     * "bItem":"details of back card design" {
     * "image":{"id":"Background image id"},
     * "textBoxes":[{
     * "content":"content of the text box",
     * "x":"x - coordinate w.r.t to the card's top-left corner in % of card's width",
     * "y":"y - coordinate w.r.t to the card's top-left corner in % of card's height",
     * "w":"textBox's width w.r.t to the card's width in %",
     * "h":"textBox's height w.r.t to the card's width in % height"
     * }],
     * //logo is optional
     * "logo":"base64 encoded logo image file",
     * "lx":"logo's x coordinate",
     * "ly":"logo's y coordinate",
     * "lw":"logo's width",
     * "lh":"logo's height",
     * //qr is optional
     * "qr":"qr content",
     * "qrs":"qr image size in % of card's width"
     * }
     * }
     * @apiError (404) - Invalid cartItem id.
     * @apiError (400) - Invalid input data.
     * @apiError (503) - Image Service(S3) is not working.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestAttribute String id, @RequestBody RCartItem ci, HttpServletResponse response) throws SQLException, IOException {
        Customer customer = customerDAO.get(id);

        CartItem cartItem = cartItemDAO.get(ci.getId());
        if (cartItem == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else if (!cartItem.getCustomer().getId().equals(customer.getId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        RItem fRItem = ci.getfItem();
        RItem bRItem = ci.getbItem();

        Item fItem = cartItem.getfItem();
        itemDAO.updateItem(fItem, fRItem);
        if (fRItem.getImage() != null) {
            fItem.setImage(cardImageDAO.get(fRItem.getImage().getId()));
            if (!itemDAO.isValid(fItem)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Card card = fItem.getImage().getCard();
            if (card.getType() == Card.CUSTOMER_CARD) {
                Seller seller = sellerDAO.get(ci.getSellerId());
                if (seller == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                card.setSeller(seller);
                cardDAO.update(card);
            }
        }

        Item bItem = cartItem.getbItem();
        if (bRItem != null) {
            if (bItem == null) {
                bItem = new Item();
                cartItem.setbItem(bItem);
            }
            itemDAO.updateItem(bItem, bRItem);
            if (bRItem.getImage() != null) {
                bItem.setImage(cardImageDAO.get(bRItem.getImage().getId()));

                if (!itemDAO.isValid(bItem) || !bItem.getImage().getCard().getSeller().getId().
                        equals(fItem.getImage().getCard().getSeller().getId()) || bItem.getImage().getCard().getType() == Card.CUSTOMER_CARD) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }
        }
        if (isNotEmpty(fRItem.getLogo())) {
            String filename = customer.getId() + "f" + new Date().getTime() + ".png";
            if (!imageService.writeLogoImage(fRItem.getLogo(), filename)) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return;
            }
        }
        if (bRItem != null && isNotEmpty(bRItem.getLogo())) {
            String filename = customer.getId() + "b" + new Date().getTime() + ".png";
            if (!imageService.writeLogoImage(bRItem.getLogo(), filename)) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return;
            }
        }
        cartItemDAO.update(cartItem);
    }

    /*@RequestMapping(value = "/buildCart", method = RequestMethod.POST)
    public boolean buildCart(@RequestBody ArrayList<RCartItem> cartItems, @RequestHeader("Authorization") String token) throws SQLException {

        Customer customer = customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer == null) return false;

        for (RCartItem rCI : cartItems) {
            CartItem cartItem = cartItemDAO.get(rCI.getId());
            cartItem.setFinishSeller(finishSellerDAO.get(rCI.getFsId()));
            cartItem.setPaperSeller(paperSellerDAO.get(rCI.getPsId()));
            cartItem.setQuantity(rCI.getQuantity());
            cartItemDAO.update(cartItem);
        }

        return true;
    }*/

    /**
     * @api {get} /customer/cart/total Cart Value.
     * @apiDescription Total cart value.
     * @apiGroup Cart
     * @apiSuccess  {Float} - Total cart value in dollars.
     */
    @RequestMapping("/total")
    public Float getTotal(@RequestAttribute String id) throws SQLException {
        Customer customer = customerDAO.get(id);
        return cartItemDAO.getTotalCartPrice(customer);
    }
}
