package com.neo.Controller;

import com.neo.DAO.AdvertisementDAO;
import com.neo.DAO.AdvertisementSellerDAO;
import com.neo.DatabaseModel.Advertisement;
import com.neo.DatabaseModel.AdvertisementSeller;
import com.neo.GeocodingHelper;
import com.neo.Model.RAdvertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ad")
public class AdvertisementController {

    private final AdvertisementDAO advertisementDAO;
    private final GeocodingHelper geocodingHelper;
    private final AdvertisementSellerDAO advertisementSellerDAO;

    @Autowired
    public AdvertisementController(AdvertisementDAO advertisementDAO, GeocodingHelper geocodingHelper, AdvertisementSellerDAO advertisementSellerDAO) {
        this.advertisementDAO = advertisementDAO;
        this.geocodingHelper = geocodingHelper;
        this.advertisementSellerDAO = advertisementSellerDAO;
    }

    /**
     * @api {get} /ad/rate Advertisement Rates
     * @apiGroup Advertisement
     * @apiDescription Get advertisement commission rates($) for different coverage of operation.
     * @apiSuccess {Float} city City coverage commission.
     * @apiSuccess {Float} state State coverage commission.
     * @apiSuccess {Float} country Country coverage commission.
     * @apiSuccess {Float} international International coverage commission.
     */
    @RequestMapping("/rate")
    public RAdvertisement getAdvertisementRate() throws SQLException {

        List<Advertisement> advertisements = advertisementDAO.listAll();
        RAdvertisement rAdvertisement=new RAdvertisement();

        for(Advertisement advertisement: advertisements){
            switch (advertisement.getType()){
                case Advertisement.CITY_TYPE:
                    rAdvertisement.setCity(advertisement.getAmt());
                    break;
                case Advertisement.STATE_TYPE:
                    rAdvertisement.setState(advertisement.getAmt());
                    break;
                case Advertisement.COUNTRY_TYPE:
                    rAdvertisement.setCountry(advertisement.getAmt());
                    break;
                case Advertisement.INTERNATIONAL_TYPE:
                    rAdvertisement.setInternational(advertisement.getAmt());
                    break;
            }
        }
        return rAdvertisement;

    }

    /**
     * @api {get} /ad Advertisement
     * @apiGroup Advertisement
     * @apiDescription Get advertisement posters for a location.
     * @apiParam {Double} lat Latitude of the current location.
     * @apiParam {Double} lng Longitude of the current location.
     * @apiSuccess {String[]} - Advertisement poster filenames.
     */
    @RequestMapping("")
    public List<String> getAdvertisements(@RequestParam double lat, double lng){
        ArrayList<String> advertisements=new ArrayList<>();

        String[] strings=geocodingHelper.getShortCSC(lat,lng);
        List<AdvertisementSeller> advertisementSellers=advertisementSellerDAO.getAdvertisements(strings[0], strings[1], strings[2]);

        for(AdvertisementSeller advertisementSeller: advertisementSellers)
            advertisements.add(advertisementSeller.getPoster());

        return advertisements;
    }
}