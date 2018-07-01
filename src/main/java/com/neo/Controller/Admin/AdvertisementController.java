package com.neo.Controller.Admin;

import com.neo.DAO.AdvertisementDAO;
import com.neo.DatabaseModel.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.neo.DatabaseModel.Advertisement.*;

@RestController("adminAdController")
@RequestMapping("/admin/ad")
public class AdvertisementController {

    private final AdvertisementDAO advertisementDAO;

    @Autowired
    public AdvertisementController(AdvertisementDAO advertisementDAO) {
        this.advertisementDAO = advertisementDAO;
    }

    /**
     * @api {get} /admin/ad/update Update Ad
     * @apiGroup Advertisement
     * @apiDescription Update advertisement commission.
     * @apiParam {Float} amt Amount in $.
     * @apiParam {Integer="1->City coverage", "2->State coverage", "3->Country coverage", "4->International coverage"} type Coverage of advertisement.
     * @apiError (401) - "type" parameter is invalid.
     */
    @RequestMapping("/update")
    public void UpdateAdvertisement(@RequestParam float amt, int type, HttpServletResponse response) {
        if (type != CITY_TYPE && type != STATE_TYPE && type != COUNTRY_TYPE && type!=INTERNATIONAL_TYPE)
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
            advertisementDAO.update(new Advertisement(type, amt));
    }
}