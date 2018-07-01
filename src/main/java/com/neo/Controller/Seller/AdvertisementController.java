package com.neo.Controller.Seller;

import com.neo.DAO.AdminDAO;
import com.neo.DAO.AdvertisementDAO;
import com.neo.DAO.AdvertisementSellerDAO;
import com.neo.DAO.SellerDAO;
import com.neo.DatabaseModel.Advertisement;
import com.neo.DatabaseModel.AdvertisementSeller;
import com.neo.DatabaseModel.PaymentOrder;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.GeocodingHelper;
import com.neo.Model.RSellerAdvertisement;
import com.neo.PayPalHelper;
import com.neo.Service.ImageService;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController("sellerAdController")
@RequestMapping("/seller/ad")
public class AdvertisementController {

    private final SellerDAO sellerDAO;
    private final GeocodingHelper geocodingHelper;
    private final ImageService imageService;
    private final AdvertisementSellerDAO advertisementSellerDAO;
    private final PayPalHelper payPalHelper;
    private final AdvertisementDAO advertisementDAO;

    @Autowired
    public AdvertisementController(SellerDAO sellerDAO, AdvertisementDAO advertisementDAO, GeocodingHelper geocodingHelper, ImageService imageService,
                                   AdvertisementSellerDAO advertisementSellerDAO, AdminDAO adminDAO, PayPalHelper payPalHelper, AdvertisementDAO advertisementDAO1) {
        this.sellerDAO = sellerDAO;
        this.geocodingHelper = geocodingHelper;
        this.imageService = imageService;
        this.advertisementSellerDAO = advertisementSellerDAO;
        this.payPalHelper = payPalHelper;
        this.advertisementDAO = advertisementDAO1;
    }

    /**
     * @api {post} /seller/ad/new Upload advertisement.
     * @apiDescription Upload advertisement image. This will be shown to different users publicly according to the scheme chosen.
     * @apiGroup Advertisement
     * @apiParamExample  {json} Example:-
     * [{
     *     "poster": Base64 image of the ad,
     *     "type": Coverage type:- 1->City, 2->State, 3->Country, 4->International,
     *     "name": Advertisement name,
     *     "sdate": Starting date of the ad in "YYYY-MM-DD" format,
     *     "edate": Ending date of the ad in "YYYY-MM-DD" format
     * }]
     * @apiError (402) - Payment Service not working.
     * @apiError (400) - Either the dates or the type is not correct.
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void NewAdvertisement(@RequestBody RSellerAdvertisement rSellerAdvertisement, @RequestAttribute String id, HttpServletResponse response) throws SQLException, IOException, PayPalRESTException {
        Seller seller = sellerDAO.get(id);

        if (!sellerDAO.verified(seller)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return ;
        }

        long eTimeStamp = rSellerAdvertisement.getEdate().getTime();
        long sTimeStamp = rSellerAdvertisement.getSdate().getTime();
        long diff = eTimeStamp - sTimeStamp;
        float cost = advertisementDAO.getAdPrice(rSellerAdvertisement.getType());
        if(cost==-1 || diff<0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ;
        }
        long d = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
        cost*=d;

        String saleId = payPalHelper.executePayment(rSellerAdvertisement.getPayerID(), rSellerAdvertisement.getPaymentID(), cost);
        if (saleId == null){
            response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
            return;
        }

        String filename = seller.getId() + "_" + new Date().getTime() + ".png";
        imageService.writeAdImage(rSellerAdvertisement.getPoster(), filename);

        AdvertisementSeller advertisementSeller = new AdvertisementSeller(seller,
                rSellerAdvertisement.getName(), filename, rSellerAdvertisement.getType(),
                sTimeStamp, eTimeStamp);

        double lat = seller.getAddress().getLat(), lng = seller.getAddress().getLng();
        String areaName = null;
        String[] components = geocodingHelper.getShortCSC(lat, lng);
        switch (rSellerAdvertisement.getType()) {
            case Advertisement.CITY_TYPE:
                areaName = components[0];
                break;
            case Advertisement.STATE_TYPE:
                areaName = components[1];
                break;
            case Advertisement.COUNTRY_TYPE:
                areaName = components[2];
                break;
        }
        advertisementSeller.setAreaName(areaName);

        advertisementSeller.setPaymentOrder(new PaymentOrder(saleId));
        advertisementSellerDAO.save(advertisementSeller);
    }
}
