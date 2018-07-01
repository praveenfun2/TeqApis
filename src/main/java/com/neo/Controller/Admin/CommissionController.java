package com.neo.Controller.Admin;


import com.neo.DAO.CommissionDAO;
import com.neo.DatabaseModel.Commission;
import com.neo.Model.RCommission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController("adminCommissionController")
@RequestMapping("/admin/commission")
public class CommissionController {

    private final CommissionDAO commissionDAO;

    @Autowired
    public CommissionController(CommissionDAO commissionDAO) {
        this.commissionDAO = commissionDAO;
    }

    @RequestMapping("/update")
    public void UpdateCommission(@RequestParam float amt, @RequestParam int type) throws SQLException {
        commissionDAO.update(new Commission(type, amt));
    }
}
