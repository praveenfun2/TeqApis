package com.neo.Controller.Admin;

import com.neo.DAO.DeliveryTypeDAO;
import com.neo.DatabaseModel.Shipment.DeliveryType;
import com.neo.Model.RDeliveryType;
import com.neo.Utils.GeneralHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/admin/delivery")
public class DeliveryTypeController {
    private final DeliveryTypeDAO deliveryTypeDAO;

    @Autowired
    public DeliveryTypeController(DeliveryTypeDAO deliveryTypeDAO) {
        this.deliveryTypeDAO = deliveryTypeDAO;
    }

    /**
     * @api {post} /admin/delivery/new New Delivery type.
     * @apiDescription Add new delivery type.
     * @apiGroup Admin
     * @apiParam {String} title Delivery type name.
     * @apiParam {String} description Delivery type description.
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Long newDeliveryType(@RequestBody RDeliveryType rDT){
        DeliveryType deliveryType=new DeliveryType(rDT.getTitle(), rDT.getDescription());
        deliveryTypeDAO.save(deliveryType);
        return deliveryType.getType();
    }

    /**
     * @api {post} /admin/delivery/update Update Delivery type.
     * @apiDescription Update delivery type.
     * @apiGroup Admin
     * @apiParam {Long} type Delivery type id.
     * @apiParam [String] title Delivery type name.
     * @apiParam [String] description Delivery type description.
     * @apiError (404) - Invalid type/id.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody RDeliveryType rDT, HttpServletResponse response){
        DeliveryType deliveryType=deliveryTypeDAO.get(rDT.getType());
        if(deliveryType==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        deliveryTypeDAO.update(deliveryType, rDT);
    }
}
