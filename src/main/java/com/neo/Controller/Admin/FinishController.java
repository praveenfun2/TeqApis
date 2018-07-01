package com.neo.Controller.Admin;

import com.neo.DAO.AdminDAO;
import com.neo.DAO.FinishDAO;
import com.neo.DAO.FinishSellerDAO;
import com.neo.DAO.SellerDAO;
import com.neo.DatabaseModel.Finish;
import com.neo.Model.RFinish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestController("adminFinishController")
@RequestMapping("/admin/finish")
public class FinishController {

    private final FinishSellerDAO finishSellerDAO;
    private final FinishDAO finishDAO;
    private final SellerDAO sellerDAO;
    private final AdminDAO adminDAO;

    @Autowired
    public FinishController(FinishSellerDAO finishSellerDAO, FinishDAO finishDAO, SellerDAO sellerDAO, AdminDAO adminDAO) {
        this.finishSellerDAO = finishSellerDAO;
        this.finishDAO = finishDAO;
        this.sellerDAO = sellerDAO;
        this.adminDAO = adminDAO;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void New(@RequestBody RFinish p, HttpServletResponse response) throws SQLException {
        Finish finish = new Finish(p.getName(), p.getDescription());
        if (!finishDAO.save(finish))
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody RFinish p, HttpServletResponse response) {
        Finish finish = finishDAO.get(p.getId());
        finish.setFname(p.getName());
        finish.setfDesc(p.getDescription());
        if (finishDAO.update(finish) == null)
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    @RequestMapping("/remove")
    public void remove(@RequestParam long fid, HttpServletResponse response) {
        if (!finishDAO.delete(fid))
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

}
