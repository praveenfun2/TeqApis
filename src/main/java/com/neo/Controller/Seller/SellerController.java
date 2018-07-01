package com.neo.Controller.Seller;

import com.neo.DAO.SellerDAO;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Rating;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RAddress;
import com.neo.Model.RUser;
import com.neo.Service.AddressService;
import com.neo.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private final SellerDAO sellerDAO;
    private final AddressService addressService;
    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerDAO sellerDAO, AddressService addressService, SellerService sellerService) {
        this.sellerDAO = sellerDAO;
        this.addressService = addressService;
        this.sellerService = sellerService;
    }


    /**
     * @api {get} /seller Seller info.
     * @apiDescription Get seller dashboard information.
     * @apiGroup Seller Dashboard
     * @apiSuccessExample {json} Response:-
     * {
     * "address":{
     * "postalCode": Pincode/Zipcode of the printing company's address,
     * "addressLine":Address Line,
     * "city":City,
     * "state":State,
     * "country":Country
     * },
     * "paypal":Paypal email address,
     * "ltime": Lead time for an order completion,
     * "price": Default price for card printing (for cards uploaded by users),
     * "description": Seller's company's description,
     * "name":Name used on the dashboard,
     * "reviews":[{
     * "review":Customer's review,
     * "reviewer":Customer's name",
     * "rating":Rating based on 5 stars
     * }]
     * }
     * @apiError (404) - Email Address is not registered
     * @apiError (400) - Invalid data.
     */

    @RequestMapping("")
    public RUser getSeller(@RequestAttribute String id, @RequestParam(defaultValue = "false") boolean reviews) throws SQLException {
        Seller seller = sellerDAO.get(id);

        RUser rSeller = new RUser();
        sellerService.buildRSeller(seller, rSeller);

        if (reviews)
            for (Rating rating : seller.getRatings())
                rSeller.addReview(rating.getReview(), rating.getUser().getName(), rating.getRating());

        return rSeller;
    }

    /**
     * @api {post} /seller/update Update seller.
     * @apiDescription Update dashboard information of the seller.
     * @apiGroup Seller Dashboard
     * @apiParamExample {json} Request:-
     * {
     * //Below are optional
     * "address":{"postalCode": "Pincode/Zipcode of the printing company's address", "addressLine":"Address Line"},
     * "paypal":"Paypal email address",
     * "ltime":"Lead time for an order completion",
     * "price":"Default price for card printing (for cards uploaded by users)",
     * "description": "Seller's company's description",
     * "name":"Name used on the dashboard",
     * "phone": "Phone number"
     * }
     * @apiError (404) - Email Address is not registered
     * @apiError (400) - Invalid data.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateSeller(@RequestBody RUser rSeller, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Seller seller = sellerDAO.get(id);

        RAddress naddress = rSeller.getAddress();
        if (naddress != null) {
            Address address = new Address();
            addressService.update(naddress, address);
            if (!addressService.isValid(address)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            seller.setAddress(address);
        }

        sellerService.update(rSeller, seller);

        sellerDAO.update(seller);
    }

    /*@RequestMapping("/approval")
    public void saveApproval(@RequestParam int type, String name, @RequestParam(required = false) String des,
                             @RequestParam(required = false) String subcat, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Seller seller = sellerDAO.get(id);

        if (!seller.isValidated()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Approval approval = new Approval(name, type, seller, subcat, des);
        approvalDAO.save(approval);

    }*/



    /*@RequestMapping("/getFilterSellers")
    public List<RSeller> getFilterSeller(@RequestParam (defaultValue = "0") Integer minprice,
                                              @RequestParam (defaultValue = Integer.MAX_VALUE+"") Integer maxprice,
                                              @RequestParam(required = false) String pqid,
                                              @RequestParam(required = false) String fid,
                                              @RequestParam(required = false) Float lat,
                                              @RequestParam(required = false) Float lon,
                                              @RequestParam(required = false) String order) {

        Session session = sessionFactory.openSession();

        String qr = "select id, name, rating, price, ltime, description, address.id," +
                " :distance_unit" +
                "* DEGREES(ACOS(COS(RADIANS( :latpoint))" +
                "* COS(RADIANS(lat))" +
                "* COS(RADIANS(:longpoint) - RADIANS(lon))" +
                "+ SIN(RADIANS(:latpoint))" +
                "* SIN(RADIANS(lat)))) as distance_in_km from Seller " +
                "where price >= :minPrice and price <= :maxPrice";

        ArrayList<String> sids = new ArrayList<>();
        if (isNotEmpty(pqid)) {
            String[] pqids = pqid.split(",");
            Query psQuery = session.createQuery("select seller.id from PaperSeller" +
                    " where paper.pqid in :pqids")
                    .setParameterList("pqids", pqids);
            sids.addAll(psQuery.list());
        }

        if (isNotEmpty(fid)) {
            String[] fids = fid.split(",");
            Query fsQuery = session.createQuery("select seller.id from FinishSeller" +
                    " where finish.fid in :fids")
                    .setParameterList("fids", fids);
            sids.addAll(fsQuery.list());
        }
        if(sids.size()>0) qr+=" and id in :sids";

        if (isNotEmpty(order))
            switch (order) {
                case SellerCard.SORT_BY_DISTANCE:
                    qr += "  order by distance_in_km";
                    break;
            }

        Query query = session.createQuery(qr)
                .setFloat("latpoint", lat)
                .setFloat("longpoint", lon)
                .setFloat("distance_unit", 111.045f)
                .setInteger("minPrice", minprice)
                .setInteger("maxPrice", maxprice);

        List<Object[]> rows = query.list();

        List<RSeller> a = new ArrayList<>();

        for (Object[] row : rows) {

            String sid = (String) row[0];
            String name = (String) row[1];
            double rating = (double) row[2];
            float price = (float) row[3];
            int ltime = (int) row[4];
            int distance = ((Double) row[7]).intValue();
            String des = (String) row[5];
            Long addressId = Long.parseLong(String.valueOf(row[6]));

            RSeller seller = new RSeller(name, des, sid, price, ltime, distance, rating);

            Criteria criteria = session.createCriteria(Address.class);
            criteria.add(Restrictions.idEq(addressId));
            Address address=(Address) criteria.list().get(0);
            RAddress rAddress=new RAddress()
            seller.setAddress(rAddress);

            a.add(seller);

        }

        session.flush();
        session.close();

        return a;

    }*/


}
