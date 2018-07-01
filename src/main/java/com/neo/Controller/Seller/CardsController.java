package com.neo.Controller.Seller;

import com.neo.DAO.*;
import com.neo.DatabaseModel.Card.SellerCard;
import com.neo.DatabaseModel.Card.SellerCardBack;
import com.neo.DatabaseModel.Card.SellerCardFront;
import com.neo.DatabaseModel.*;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RCard;
import com.neo.Model.RCards;
import com.neo.Model.RPromotion;
import com.neo.Service.CardService;
import com.neo.Service.ImageService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.neo.DatabaseModel.Card.Card.*;
import static com.neo.Utils.GeneralHelper.round2;

@RestController("sellerCardController")
@RequestMapping("/seller/card")
public class CardsController {

    private final SessionFactory sessionFactory;
    private final BASE64Decoder decoder;
    private final SellerDAO sellerDAO;
    private final CategoryDAO categoryDAO;
    private final SubCategoryDAO subCategoryDAO;
    private final CardsSubcatDAO cardsSubcatDAO;
    private final CardsPaperDAO cardsPaperDAO;
    private final CardsFinishDAO cardsFinishDAO;
    private final PaperSellerDAO paperSellerDAO;
    private final FinishSellerDAO finishSellerDAO;
    private final PromotionDAO promotionDAO;
    private final CardService cardService;
    private final SellerCardBackDAO backDAO;
    private final SellerCardFrontDAO frontDAO;
    private final SellerCardDAO sellerCardDAO;
    private final CardImageDAO cardImageDAO;
    private final CartItemDAO cartItemDAO;
    private final ImageService imageService;

    @Autowired
    public CardsController(SessionFactory sessionFactory, SellerDAO sellerDAO, CategoryDAO categoryDAO, SubCategoryDAO subCategoryDAO,
                           CardsSubcatDAO cardsSubcatDAO, CardsPaperDAO cardsPaperDAO,
                           CardsFinishDAO cardsFinishDAO, PaperSellerDAO paperSellerDAO,
                           FinishSellerDAO finishSellerDAO, PromotionDAO promotionDAO, CustomerDAO customerDAO, SellerCardFrontDAO frontDAO,
                           SellerCardBackDAO backDAO, CustomerCardDAO customerCardDAO, CardDAO cardDAO, UserDAO userDAO, CardService cardService, SellerCardDAO sellerCardDAO, CardImageDAO cardImageDAO, CartItemDAO cartItemDAO, ImageService imageService) {
        this.sessionFactory = sessionFactory;
        this.cardService = cardService;
        this.cardImageDAO = cardImageDAO;
        this.imageService = imageService;
        this.sellerDAO = sellerDAO;
        this.categoryDAO = categoryDAO;
        this.subCategoryDAO = subCategoryDAO;
        this.cardsSubcatDAO = cardsSubcatDAO;
        this.cardsPaperDAO = cardsPaperDAO;
        this.cardsFinishDAO = cardsFinishDAO;
        this.paperSellerDAO = paperSellerDAO;
        this.finishSellerDAO = finishSellerDAO;
        this.promotionDAO = promotionDAO;
        this.frontDAO = frontDAO;
        this.backDAO = backDAO;
        this.sellerCardDAO = sellerCardDAO;
        this.cartItemDAO = cartItemDAO;
        decoder = new BASE64Decoder();
    }

    /**
     * @api {post} /seller/card/new New Card
     * @apiDescription Upload a new card desgin.
     * @apiGroup Cards
     * @apiParam {String} image Base64 encoded image data. Image may contain transparent data.
     * All the transparent points in the image will be filled with chosen colors.
     * @apiParam {String[]} color Hexadecimal color array to fill the image.
     * @apiParam {Boolean} landscape.
     * @apiParam {Integer} side 1->Front side, 2->Back Side.
     * @apiParam {Float} price Price of the card. Should be only upto 2 decimal places precise.
     * @apiParam {String} name Card name.
     * @apiParam {Long} cat Category id allotted to the card. Applicable only for front side card design.
     * @apiParam {Long[]} subcat Sub-category ids allotted to the card. Applicable only for front side card design.
     * @apiParam {Long[]} paper Seller specific paper Quality ids allotted to the card. Applicable only for front side card design.
     * @apiParam {Long[]} finish Seller specific paper Finish ids allotted to the card. Applicable only for front side card design.
     * @apiError (403) - Seller is not authorized to upload cards.
     * @apiError (503) - Image Service is not responding.
     */

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void NewCard(@RequestBody RCard rqCard, @RequestAttribute String id, HttpServletResponse response) throws SQLException, IOException {

        Seller seller = sellerDAO.get(id);
        ArrayList<String> paths = new ArrayList<>();
        String file = new Date().getTime() + "";

        BufferedImage bufferedImage = imageService.readImage(rqCard.getEncodedImage());

        for (String cc : rqCard.getColor()) {
            BufferedImage bufferedImage1 = imageService.fill(cc, bufferedImage);

            //Scale image
            BufferedImage image = imageService.scaleImage(bufferedImage1, rqCard.isLandscape());

            String filename = file + "_" + cc + "_" + ".png";
            paths.add(filename);

            //ByteArrayInputStream inputStream = imageService.bufferedImageToInputStream(image);

            if (!imageService.writeCardImage(image, filename)) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return;
            }
        }

        SellerCard card = null;
        float price = round2(rqCard.getPrice());
        switch (rqCard.getSide()) {
            case SELLER_CARD_FRONT:
                SellerCardFront front = new SellerCardFront(price, rqCard.isLandscape(), seller, rqCard.getName(), categoryDAO.get(rqCard.getCat()));

                if (rqCard.getSubcat() != null)
                    for (long aSa : rqCard.getSubcat()) {
                        SubCategory subCategory = subCategoryDAO.get(aSa);
                        if (subCategory != null)
                            front.getSubcats().add(new CardsSubcat(front, subCategory));
                    }

                if (rqCard.getPaper() != null)
                    for (long aPa : rqCard.getPaper()) {
                        PaperSeller paperSeller = paperSellerDAO.get(aPa, seller.getId());
                        if (paperSeller != null)
                            front.getPapers().add(new CardsPaper(front, paperSeller));
                    }

                if (rqCard.getFinish() != null)
                    for (long aFa : rqCard.getFinish()) {
                        FinishSeller finishSeller = finishSellerDAO.get(aFa, seller.getId());
                        if (finishSeller != null)
                            front.getFinishes().add(new CardsFinish(front, finishSeller));
                    }

                frontDAO.save(front);
                card = front;
                break;
            case SELLER_CARD_BACK:
                SellerCardBack back = new SellerCardBack(price, rqCard.isLandscape(), seller, rqCard.getName());
                backDAO.save(back);
                card = back;
                break;
        }

        for (int i = 0; i < paths.size(); i++) {
            String filename = paths.get(i);
            String color = rqCard.getColor().get(i);
            cardImageDAO.save(new CardImage(filename, card, color));
        }
    }

    /**
     * @api {get} /seller/card Card Designs.
     * @apiDescription Get all the card designs uploaded by the seller.
     * @apiGroup Cards
     * @apiError (404) - Card identified by "id" is not found.
     * @apiSuccessExample {json} Response
     * {
     * "fCards":[{
     * "name":"Card name",
     * "id":card id,
     * "finishes":[paper finish names],
     * "papers":[paper quality names],
     * "images":[card images],
     * "subCategories":[card sub-categories],
     * "price":card price,
     * "category": card category,
     * "layout": 1->Landscape, 2->Portrait,
     * }],
     * "bCards":[{
     * "name":"Card name",
     * "id":card id,
     * "price":card price,
     * "layout": 1->Landscape, 2->Portrait,
     * "images":[card images]
     * }]
     * }
     */
    @RequestMapping("")
    public RCards getSellerCard(@RequestAttribute String id) throws SQLException {
        RCards rCards = new RCards();
        List<RCard> f = new ArrayList<>(), b = new ArrayList<>();
        rCards.setfCards(f);
        rCards.setbCards(b);
        Seller seller = sellerDAO.get(id);

        List<SellerCardFront> ck = seller.getFrontCards();
        for (SellerCardFront Card : ck) {
            String name = Card.getName();
            long cid = Card.getId();
            float price = Card.getPrice();

            RCard x = new RCard(name, price, Card.isLandscape());
            x.setId(cid);

            for (CardImage cardImage : Card.getImages())
                x.addCardImage(cardImage.getId(), cardImage.getFileName(), cardImage.getColor());

            List<CardsSubcat> subCategories = Card.getSubcats();
            for (CardsSubcat cS : subCategories) x.addSubCategory(cS.getSubCategory().getName());

            List<CardsPaper> cardsPapers = Card.getPapers();
            for (CardsPaper cP : cardsPapers) x.addPaper(cP.getPaper().getPaper().getName());

            List<CardsFinish> cardsFinishes = Card.getFinishes();
            for (CardsFinish cF : cardsFinishes) x.addFinish(cF.getFinish().getFinish().getFname());

            Category category = Card.getCategory();
            if (category != null)
                x.setCategory(category.getName());

            f.add(x);
        }

        List<SellerCardBack> sellerCardBacks = seller.getBackCards();
        for (SellerCardBack Card : sellerCardBacks) {
            String name = Card.getName();
            long cid = Card.getId();
            float price = Card.getPrice();

            RCard x = new RCard(name, price, Card.isLandscape());
            x.setId(cid);
            for (CardImage cardImage : Card.getImages())
                x.addCardImage(cardImage.getId(), cardImage.getFileName(), cardImage.getColor());

            b.add(x);
        }

        return rCards;
    }

    /**
     * @api {get} /seller/card/remove Remove Card design.
     * @apiDescription Remove seller uploaded card design.
     * @apiGroup Cards
     * @apiParam {Long} cid card id.
     * @apiError (404) - Card identified by "id" is not found.
     * @apiError (403) - User is unauthorized to delete this card.
     */
    @RequestMapping("/remove")
    public void removeCard(@RequestAttribute String id, @RequestParam long cid, HttpServletResponse response) throws SQLException {
        Seller seller = sellerDAO.get(id);

        SellerCard sellerCard = sellerCardDAO.get(cid);
        if (sellerCard == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else if (!sellerCard.getSeller().getId().equals(seller.getId()))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            for (CardImage cardImage : sellerCard.getImages())
                imageService.deleteCard(cardImage.getFileName());
            sellerCardDAO.delete(sellerCard);
        }

    }

    /**
     * @api {post} /seller/card/promotion Card promotion.
     * @apiDescription Provide card discount prices.
     * @apiGroup Cards
     * @apiParamExample {json} Request:-
     * {
     * "name": Name of the promotion,
     * "discount": Discount percentage,
     * "sdate": Promotion start date (yyyy-MM-dd),
     * "edate": Promotion end date (yyyy-MM-dd),
     * "cids": Card id array to apply the promotion to
     * }
     */
    @RequestMapping(value = "/promotion", method = RequestMethod.POST)
    public void NewPromotion(@RequestBody RPromotion rPromotion, @RequestAttribute String id) throws SQLException {
        Seller seller = sellerDAO.get(id);

        Promotion promotion = new Promotion(rPromotion.getName(), rPromotion.getDiscount(), rPromotion.getSdate().getTime(), rPromotion.getEdate().getTime());
        promotionDAO.save(promotion);
        for (Long cid : rPromotion.getCids()) {
            SellerCard card = sellerCardDAO.get(cid);
            if (card != null && card.getSeller().getId().equalsIgnoreCase(seller.getId())) {
                card.setPromotion(promotion);
                sellerCardDAO.update(card);
            }
        }
    }

    /**
     * @api {post} /seller/card/update Update Card
     * @apiDescription Update an existing card design
     * @apiGroup Cards
     * @apiParam {Long} id Card id.
     * @apiParam {Integer} layout 1->Landscape, 2->Portrait.
     * @apiParam {Float} price Price of the card. Should be only upto 2 decimal places precise.
     * @apiParam {String} name Card name.
     * @apiParam {Long} cat Category id allotted to the card. Applicable only for front side card design.
     * @apiParam {Long[]} subcat Sub-category ids allotted to the card. Applicable only for front side card design.
     * @apiParam {Long[]} paper Seller specific paper Quality ids allotted to the card. Applicable only for front side card design.
     * @apiParam {Long[]} finish Seller specific paper Finish ids allotted to the card. Applicable only for front side card design.
     * @apiError (403) - Seller is not forbidden to update the card.
     * @apiError (400) - The request params are malformed.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void UpdateCard(@RequestBody RCard rCard, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Seller seller = sellerDAO.get(id);
        SellerCard sellerCard = sellerCardDAO.get(rCard.getId());

        if (sellerCard == null || !sellerCard.getSeller().getId().equals(seller.getId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        switch (sellerCard.getType()) {
            case SELLER_CARD_FRONT:
                SellerCardFront card = frontDAO.get(rCard.getId());
                cardService.update(rCard, card);
                if (cardService.isValid(card))
                    frontDAO.update(card);
                else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                break;

            case SELLER_CARD_BACK:
                SellerCardBack back = backDAO.get(rCard.getId());
                cardService.update(rCard, back);
                if (cardService.isValid(back))
                    backDAO.update(back);
                else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                break;
        }

    }

}
