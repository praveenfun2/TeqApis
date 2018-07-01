package com.neo.Controller;

import com.neo.DAO.PaperDAO;
import com.neo.DatabaseModel.Paper;
import com.neo.Model.RPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {
    private final PaperDAO paperDAO;

    @Autowired
    public PaperController(PaperDAO paperDAO) {
        this.paperDAO = paperDAO;
    }

    /**
     * @api {get} /paper Paper Qualities
     * @apiDescription All paper qualities list.
     * @apiGroup Paper Quality
     * @apiSuccessExample {json} Response
     * [{
     * "id": "paper quality id",
     * "name": "paper quality name",
     * "description":"Description of paper quality"
     * }]
     */
    @RequestMapping("")
    public List<RPaper> getAllPaper() throws SQLException {
        List<RPaper> rPapers = new ArrayList<>();

        List<Paper> list = paperDAO.listAll();
        for (Paper p : list)
            rPapers.add(new RPaper(p.getPqid(), p.getName(), p.getDes()));

        return rPapers;
    }

}
