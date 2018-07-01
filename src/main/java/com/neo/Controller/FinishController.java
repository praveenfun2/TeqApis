package com.neo.Controller;

import com.neo.DAO.AdminDAO;
import com.neo.DAO.FinishDAO;
import com.neo.DAO.FinishSellerDAO;
import com.neo.DAO.SellerDAO;
import com.neo.DatabaseModel.Finish;
import com.neo.Model.RFinish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/finish")
public class FinishController {

    private final FinishDAO finishDAO;

    @Autowired
    public FinishController(FinishSellerDAO finishSellerDAO, FinishDAO finishDAO, SellerDAO sellerDAO, AdminDAO adminDAO) {
        this.finishDAO = finishDAO;
    }

    /**
     * @api {get} /finish Paper Finishes
     * @apiDescription All paper finishes list.
     * @apiGroup Paper Finish
     * @apiSuccessExample {json} Response
     * [{
     * "id": "paper finish id",
     * "name": "paper finish name",
     * "description":"Description of paper finish"
     * }]
     */
    @RequestMapping("")
    public List<RFinish> getAllFinish() throws SQLException {
        List<RFinish> rFinishes = new ArrayList<>();

        List<Finish> finishes = finishDAO.listAll();
        for (Finish f : finishes)
            rFinishes.add(new RFinish(f.getFid(), f.getFname(), f.getfDesc()));

        return rFinishes;
    }

}
