package com.neo.Controller.Admin;


import com.neo.DAO.APIDAO;
import com.neo.DatabaseModel.API;
import com.neo.DatabaseModel.Users.Admin;
import com.neo.EmailHelper;
import com.neo.GeocodingHelper;
import com.neo.Model.RAPI;
import com.neo.PayPalHelper;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/API")
public class APIController {

    private final APIDAO apidao;
    private final PayPalHelper payPalHelper;
    private final GeocodingHelper geocodingHelper;
    private final EmailHelper emailHelper;

    @Autowired
    public APIController(APIDAO apidao, PayPalHelper payPalHelper, GeocodingHelper geocodingHelper, EmailHelper emailHelper) {
        this.apidao = apidao;
        this.payPalHelper = payPalHelper;
        this.geocodingHelper = geocodingHelper;
        this.emailHelper = emailHelper;
    }

    /**
     * @api {get} /admin/API Api
     * @apiGroup API
     * @apiDescription Get all api credentials.
     * @apiSuccessExample {json} Response
     * {
     *     "geocoding":{
     *         "id": Client id
     *     },
     *     "email":{
     *         "id": Client id
     *     },
     *     "paypal":{
     *         "id": Client id,
     *         "secret": Client secret
     *     }
     * }
     */
    @RequestMapping("")
    public RAPI getAPI(){
        RAPI rapi=new RAPI();

        API paypal=apidao.paypalApi(), email=apidao.emailApi(), geocoding=apidao.geocodingApi();
        if(paypal!=null)
            rapi.setPaypal(new API(paypal.getId(), paypal.getSecret()));
        if (email!=null)
            rapi.setEmail(new API(email.getId(), email.getSecret()));
        if(geocoding!=null)
            rapi.setGeocoding(new API(geocoding.getId(), geocoding.getSecret()));

        return rapi;
    }

    /**
     * @api {post} /admin/API/update/paypal Paypal Api
     * @apiGroup API
     * @apiDescription Update paypal Api.
     * @apiParam {String} id paypal client id.
     * @apiParam {String} secret paypal client secret.
     */
    @RequestMapping(value = "/update/paypal", method = RequestMethod.POST)
    public void updatePaypalAPI(@RequestBody API api){
        api.setType(API.PAYPAL);
        apidao.update(api);
        payPalHelper.setApiChanged();
    }

    /**
     * @api {post} /admin/API/update/geocoding Geocoding Api
     * @apiGroup API
     * @apiDescription Update geocoding Api.
     * @apiParam {String} id Google geocoding client id.
     */
    @RequestMapping(value = "/update/geocoding", method = RequestMethod.POST)
    public void updateGeocodingAPI(@RequestBody API api){
        api.setType(API.GEOCODING);
        apidao.update(api);
        geocodingHelper.setApiChanged();
    }

    /**
     * @api {post} /admin/API/update/email Email Api
     * @apiGroup API
     * @apiDescription Update email Api.
     * @apiParam {String} id Sendgrid client id.
     */
    @RequestMapping(value = "/update/email", method = RequestMethod.POST)
    public void updateEmailAPI(@RequestBody API api){
        api.setType(API.EMAIL);
        apidao.update(api);
        emailHelper.setApiChanged();
    }
}
