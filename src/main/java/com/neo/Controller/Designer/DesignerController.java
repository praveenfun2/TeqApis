package com.neo.Controller.Designer;

import com.neo.DAO.DesignerDAO;
import com.neo.DatabaseModel.Rating;
import com.neo.DatabaseModel.Users.Designer;
import com.neo.Model.RUser;
import com.neo.Service.AddressService;
import com.neo.Service.DesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@RequestMapping("/designer")
public class DesignerController {

    private final DesignerDAO designerDAO;
    private final DesignerService designerService;
    private final AddressService addressService;

    @Autowired
    public DesignerController(DesignerDAO designerDAO, DesignerService designerService, AddressService addressService) {
        this.designerDAO = designerDAO;
        this.designerService = designerService;
        this.addressService = addressService;
    }

    /**
     * @api {post} /designer/update Update designer.
     * @apiDescription Update dashboard information.
     * @apiGroup Designer Dashboard
     * @apiParamExample {json} Request:-
     * {
     * //Below are optional
     * "paypal":"Paypal email address",
     * "name":"Name used on the dashboard",
     * "phone": "Phone number"
     * }
     * @apiError (404) - Email Address is not registered
     * @apiError (400) - Invalid data.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateDesigner(@RequestBody RUser rDesigner, @RequestAttribute String id) throws SQLException {
        Designer designer = designerDAO.get(id);
        designerService.update(designer, rDesigner);
    }

    /**
     * @api {get} /designer Designer info.
     * @apiDescription Get designer dashboard information.
     * @apiGroup Designer Dashboard
     * @apiSuccessExample {json} Response:-
     * {
     * "paypal":Paypal email address,
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
    public RUser getDesigner(@RequestAttribute String id, @RequestParam(defaultValue = "false") boolean reviews) throws SQLException {
        Designer designer = designerDAO.get(id);

        RUser rDesigner = new RUser(designer.getId(), designer.getName(), designer.getPaypal());
        //rDesigner.setVerified(designer.isValidated());

        if (reviews)
            for (Rating rating : designer.getRatings())
                rDesigner.addReview(rating.getReview(), rating.getUser().getName(), rating.getRating());
        /*Address a = designer.getAddress();
        if (a != null) {
            RAddress address = new RAddress(addressService.addressString(a), a.getName(), a.getPhone(), null);
            rDesigner.setAddress(address);
        }*/

        return rDesigner;
    }

    /*@RequestMapping("/checkDesigner")
    public boolean checkSeller(@RequestHeader("Authorization") String token) {
        Designer designer = designerDAO.get(JWTHelper.decodeToken(token).getId());
        if (designer == null || !designer.isValidated()) return false;

        return true;
    }*/
}
