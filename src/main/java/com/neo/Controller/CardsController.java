package com.neo.Controller;

import com.neo.DAO.*;
import com.neo.DatabaseModel.Card.*;
import com.neo.DatabaseModel.*;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.Distance;
import com.neo.Model.RCard;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.neo.DatabaseModel.Card.Card.SELLER_CARD_BACK;
import static com.neo.DatabaseModel.Card.Card.SELLER_CARD_FRONT;
import static com.neo.Utils.GeneralHelper.isNotEmpty;

@RestController
@CrossOrigin
@RequestMapping("/card")
public class CardsController {

    private final SessionFactory sessionFactory;
    private final PromotionDAO promotionDAO;
    private final CustomerCardDAO customerCardDAO;
    private final CardDAO cardDAO;
    private final SellerCardBackDAO backDAO;
    private final SellerCardFrontDAO frontDAO;
    private final SellerCardDAO sellerCardDAO;

    @Autowired
    public CardsController(SessionFactory sessionFactory, PromotionDAO promotionDAO, CustomerCardDAO customerCardDAO, CardDAO cardDAO,
                           SellerCardBackDAO backDAO, SellerCardFrontDAO frontDAO, SellerCardDAO sellerCardDAO) {
        this.sessionFactory = sessionFactory;
        this.promotionDAO = promotionDAO;
        this.customerCardDAO = customerCardDAO;
        this.cardDAO = cardDAO;
        this.backDAO = backDAO;
        this.frontDAO = frontDAO;
        this.sellerCardDAO = sellerCardDAO;
    }

    /**
     * @api {get} /card/filter/ Card List
     * @apiDescription Get a list of card designs filtered based on parameters passed.
     * @apiGroup Cards
     * @apiParam {Integer} [minPrice =0] Minimum price for the cards.
     * @apiParam {Integer} [maxPrice = INT_MAX] Maximum price for the cards.
     * @apiParam {Integer=1, 2} type type of card: 1->front design, 2->back design.
     * @apiParam {String[]} [sids] Ids of the printing companies.
     * @apiParam {Long[]} [cats] Ids of the Card categories.
     * @apiParam {Long[]} [subcats] Ids of the Card sub-categories.
     * @apiParam {Long[]} [pqids] Ids of the paper qualities.
     * @apiParam {Long[]} [fids] Ids of the paper finishes.
     * @apiParam {Double} [lat] Current latitude.
     * @apiParam {Double} [lon] Current longitude.
     * @apiParam {String="distance", "price"} [order] Cards are sorted based on the order.
     * @apiSuccessExample {json} Response
     * [{
     * "name":"Card name",
     * "sid":"seller id",
     * "sname":"seller name",
     * "id":"card id",
     * "images":[{
     * "id":"Image id.",
     * "fileName":"Image file name.",
     * "color":"Color variant of the card design through which the image is constructed."
     * }],
     * "price":"Card price",
     * "discountedPrice":"Price after any discount. Null if no discount available,"
     * "distance": "If lat-lon provided with request, else null" {
     * "value": "Distance of the seller from the lat-lon provided.",
     * "unit":"Km"
     * }
     * }]
     */
    @RequestMapping("/filter")
    public List<RCard> getFilterCards(@RequestParam(defaultValue = "0") int minPrice,
                                      @RequestParam(defaultValue = Integer.MAX_VALUE + "") int maxPrice,
                                      @RequestParam(required = false) String[] sids,
                                      @RequestParam(required = false) Long[] cats,
                                      @RequestParam(required = false) Long[] subcats,
                                      @RequestParam(defaultValue = SELLER_CARD_FRONT + "") Integer type,
                                      @RequestParam(required = false) Long[] pqids,
                                      @RequestParam(required = false) Long[] fids,
                                      @RequestParam(required = false) Float lat,
                                      @RequestParam(required = false) Float lon,
                                      @RequestParam(required = false) String order) throws SQLException {

        Session session = sessionFactory.openSession();

        String qr;
        if (lat != null && lon != null)
            qr = "select card.id, :distance_unit" +
                    "* DEGREES(ACOS(COS(RADIANS( :latpoint))" +
                    "* COS(RADIANS(card.seller.address.lat))" +
                    "* COS(RADIANS(:longpoint) - RADIANS(card.seller.address.lng))" +
                    "+ SIN(RADIANS(:latpoint))" +
                    "* SIN(RADIANS(card.seller.address.lat)))) as distance_in_km from SellerCard as card " +
                    "where card.price >= :minPrice and card.price <= :maxPrice and type= :type";
        else
            qr = "select card.id from SellerCard as card " +
                    "where card.price >= :minPrice and card.price <= :maxPrice and type= :type";


        if (sids != null)
            qr += " and card.seller.id in :sids";

        if (cats != null)
            qr += " and card.category.catid in :catids";

        Set<String> cids = new HashSet<>();
        if (subcats != null) {
            Query cidquery = session.createQuery("select subcat.card.cid " +
                    "from CardsSubcat as subcat where subcat.id in :subids")
                    .setParameterList("subids", subcats);
            cids.addAll(cidquery.list());
        }

        if (pqids != null) {
            Query psidQuery = session.createQuery("select paper.id " +
                    "from PaperSeller as paper where paper.paper.pqid in :pqids")
                    .setParameterList("pqids", pqids);
            Query cidQuery = session.createQuery("select paper.card.id from CardsPaper as paper" +
                    " where paper.paper.id in :psids")
                    .setParameterList("psids", psidQuery.list());
            cids.addAll(cidQuery.list());
        }

        if (fids != null) {
            Query fsidQuery = session.createQuery("select finish.id " +
                    "from FinishSeller as finish where finish.finish.fid in :fids")
                    .setParameterList("fids", fids);
            Query cidQuery = session.createQuery("select finish.card.id from CardsFinish as finish" +
                    " where finish.finish.id in :fsids")
                    .setParameterList("fsids", fsidQuery.list());
            cids.addAll(cidQuery.list());
        }

        if (cids.size() > 0) qr += " and cid in :cids";

        if (isNotEmpty(order))
            switch (order) {
                case SellerCard.SORT_BY_DISTANCE:
                    if (lat != null && lon != null)
                        qr += " order by distance_in_km";
                    break;
                case SellerCard.SORT_BY_PRICE:
                    qr += " order by card.price";
                    break;
            }

        Query query = session.createQuery(qr)
                .setInteger("minPrice", minPrice)
                .setInteger("maxPrice", maxPrice);
        if (type == Card.SELLER_CARD_FRONT || type == Card.SELLER_CARD_BACK)
            query.setInteger("type", type);
        if (lat != null && lon != null) {
            query.setFloat("latpoint", lat);
            query.setFloat("longpoint", lon);
            query.setFloat("distance_unit", 111.045f);
        }

        if (sids != null) query.setParameterList("sids", sids);
        if (cats != null) query.setParameterList("catids", cats);
        if (cids.size() > 0) query.setParameterList("cids", cids);

        List<RCard> a = new ArrayList<>();

        List<Object[]> rows = query.list();
        session.flush();
        session.close();

        for (Object[] row : rows) {
            long cid = Long.parseLong(String.valueOf(row[0]));
            SellerCard card = sellerCardDAO.get(cid);

            String name = card.getName();
            float price = card.getPrice();
            String sid1 = card.getSeller().getId();
            String sname = card.getSeller().getName();
            RCard x = new RCard(name, sid1, sname, cid, price);

            for (CardImage ci : card.getImages())
                x.addCardImage(ci.getId(), ci.getFileName(), ci.getColor());
            if (card.getPromotion() != null)
                x.setDiscountedPrice(price - card.getPromotion().getDiscount());

            if (lat != null && lon != null) {
                Integer distance = row[1] == null ? -1 : ((Double) row[1]).intValue();
                x.setDistance(new Distance(distance, "Km"));
            }

            a.add(x);
        }

        return a;
    }

    /**
     * @api {get} /card Card Details
     * @apiDescription Get the details of a card design.
     * @apiGroup Cards
     * @apiParam {Long} id card id.
     * @apiError (404) - Card identified by "id" is not found.
     * @apiSuccessExample {json} Response
     * {
     * "name":"Card name",
     * "sid":"seller id",
     * "sname":"seller name",
     * "id":card id,
     * "finishes":[paper finish names],
     * "papers":[paper quality names],
     * "images":[{
     * "id":"Image id.",
     * "fileName":"Image file name.",
     * "color":"Color variant of the card design through which the image is constructed."
     * }],
     * "subCategories":[card sub-categories],
     * "price":card price,
     * "category": card category,
     * "side": 1->front side; 2->back side,
     * "discountedPrice":price after any discount. Null if no discount available
     * }
     */
    @RequestMapping("")
    public RCard getCardDetails(@RequestParam long id, HttpServletResponse response) throws SQLException {

        Card card = cardDAO.get(id);
        if (card == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        RCard x = new RCard();
        String name = card.getName();
        float price = card.getPrice();
        Float discountedprice = null;

        switch (card.getType()) {
            case SELLER_CARD_FRONT:
                SellerCardFront sellerCard = frontDAO.get(id);
                for (CardImage ci : sellerCard.getImages())
                    x.addCardImage(ci.getId(), ci.getFileName(), ci.getColor());

                List<CardsFinish> cardsFinishes = sellerCard.getFinishes();
                for (CardsFinish cardsFinish : cardsFinishes)
                    x.addFinish(cardsFinish.getFinish().getFinish().getFname());

                List<CardsPaper> cardsPapers = sellerCard.getPapers();
                for (CardsPaper cardsPaper : cardsPapers)
                    x.addPaper(cardsPaper.getPaper().getPaper().getName());

                List<CardsSubcat> cardsSubcats = sellerCard.getSubcats();
                for (CardsSubcat cardsSubcat : cardsSubcats)
                    x.addSubCategory(cardsSubcat.getSubCategory().getName());

                x.setCategory(sellerCard.getCategory().getName());
                x.setSide(sellerCard.getType());
                Seller user = sellerCard.getSeller();
                x.setSname(user.getName());
                x.setSid(user.getId());

                Promotion promotion = sellerCard.getPromotion();
                if (promotion != null) discountedprice = price - promotion.getDiscount();
                break;
            case SELLER_CARD_BACK:
                SellerCardBack back = backDAO.get(id);
                for (CardImage ci : back.getImages())
                    x.addCardImage(ci.getId(), ci.getFileName(), ci.getColor());

                x.setSide(card.getType());
                user = back.getSeller();
                x.setSname(user.getName());
                x.setSid(user.getId());

                promotion = back.getPromotion();
                if (promotion != null) discountedprice = price - promotion.getDiscount();
                break;
            case Card.CUSTOMER_CARD:
                CustomerCard userCard = customerCardDAO.get(id);
                Seller seller = userCard.getSeller();
                for (CardImage ci : userCard.getImages())
                    x.addCardImage(ci.getId(), ci.getFileName(), ci.getColor());
                x.setSname(seller.getName());
                x.setSid(seller.getId());
                break;
        }

        x.setPrice(price);
        x.setDiscountedPrice(discountedprice);
        x.setName(name);
        x.setId(id);

        return x;
    }

    /*@RequestMapping("/imagesAndColors")
    public RColorAndImages getCIs(@RequestParam Long cid, HttpServletResponse response) {
        SellerCard card = sellerCardDAO.get(cid);
        if (card == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        RColorAndImages rColorAndImages = new RColorAndImages();

        List<CardsColor> cardsColors = card.getColors();
        for (int i = 0; i < card.getColors().size(); i++)
            rColorAndImages.addColor(cardsColors.get(i).getColor());

        rColorAndImages.addImage(card.getImg1());
        rColorAndImages.addImage(card.getImg2());
        rColorAndImages.addImage(card.getImg3());
        rColorAndImages.addImage(card.getImg4());
        rColorAndImages.addImage(card.getImg5());

        return rColorAndImages;
    }*/

}
