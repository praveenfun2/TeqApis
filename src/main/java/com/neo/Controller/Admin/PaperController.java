package com.neo.Controller.Admin;

import com.neo.DAO.PaperDAO;
import com.neo.DatabaseModel.Paper;
import com.neo.Model.RPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestController("adminPaperController")
@RequestMapping("/admin/paper")
public class PaperController {
    private final PaperDAO paperDAO;

    @Autowired
    public PaperController(PaperDAO paperDAO) {
        this.paperDAO = paperDAO;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void NewPaper(@RequestBody RPaper p, HttpServletResponse response) throws SQLException {
        Paper paper = new Paper(p.getName(), p.getDescription());
        if (!paperDAO.save(paper))
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateP(@RequestBody RPaper p, HttpServletResponse response) {
        Paper paper = paperDAO.get(p.getId());
        paper.setName(p.getName());
        paper.setDes(p.getDescription());
        if (paperDAO.update(paper) == null)
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/remove")
    public void removeP(@RequestParam long pqid, HttpServletResponse response) {
        if (!paperDAO.delete(pqid))
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

}
