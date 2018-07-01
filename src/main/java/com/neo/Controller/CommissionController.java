package com.neo.Controller;


import com.neo.DAO.CommissionDAO;
import com.neo.DatabaseModel.Commission;
import com.neo.Model.RCommission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/commission")
public class CommissionController {
    private final CommissionDAO commissionDAO;

    @Autowired
    public CommissionController(CommissionDAO commissionDAO) {
        this.commissionDAO = commissionDAO;
    }

    /**
     * @api {get} /commission Commission
     * @apiGroup Commission
     * @apiDescription Get commission rates(%) for different service providers.
     * @apiSuccess {Float} seller Seller Commission.
     * @apiSuccess {Float} designer Designer Commission.
     * @apiSuccess {Float} courier Courier Commission.
     */
    @RequestMapping("")
    public RCommission getCommission() throws SQLException {

        RCommission rCommission = new RCommission();
        List<Commission> ck = commissionDAO.listAll();

        for (Commission commission : ck) {
            switch (commission.getType()) {
                case Commission.COURIER_TYPE:
                    rCommission.setCourier(commission.getAmt());
                    break;
                case Commission.DESIGNER_TYPE:
                    rCommission.setDesigner(commission.getAmt());
                    break;
                case Commission.SELLER_TYPE:
                    rCommission.setSeller(commission.getAmt());
                    break;
            }
        }

        return rCommission;

    }
}
