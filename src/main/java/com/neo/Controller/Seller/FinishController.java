package com.neo.Controller.Seller;

import com.neo.DAO.*;
import com.neo.DatabaseModel.Approval.FinishApproval;
import com.neo.DatabaseModel.Finish;
import com.neo.DatabaseModel.FinishSeller;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RFinish;
import com.neo.Model.RFinishSeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.neo.Utils.GeneralHelper.round2;

@RestController("sellerFinishController")
@RequestMapping("/seller/finish")
public class FinishController {

    private final FinishSellerDAO finishSellerDAO;
    private final FinishDAO finishDAO;
    private final SellerDAO sellerDAO;
    private final FinishApprovalDAO finishApprovalDAO;

    @Autowired
    public FinishController(FinishSellerDAO finishSellerDAO, FinishDAO finishDAO, SellerDAO sellerDAO, AdminDAO adminDAO, FinishApprovalDAO finishApprovalDAO) {
        this.finishSellerDAO = finishSellerDAO;
        this.finishDAO = finishDAO;
        this.sellerDAO = sellerDAO;
        this.finishApprovalDAO = finishApprovalDAO;
    }

    /**
     * @api {get} /seller/finish/new Price for paper finish.
     * @apiDescription Set the price for paper finish.
     * @apiGroup Paper Finish
     * @apiParam {Long} fid Paper finish id.
     * @apiParam {Float} price Paper finish price.
     * @apiError (404) - Paper finish not found (invalid fid).
     * @apiError (403) - User is forbidden.
     */

    @RequestMapping("/new")
    public void New(@RequestParam long fid, float price, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Seller seller = sellerDAO.get(id);
            Finish f = finishDAO.get(fid);
            if (f == null)
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            else {
                FinishSeller fs = finishSellerDAO.get(fid, seller.getId());
                if (fs != null) {
                    fs.setPrice(round2(price));
                    finishSellerDAO.update(fs);
                } else {
                    fs = new FinishSeller(seller, f, round2(price));
                    finishSellerDAO.save(fs);
                }
            }
    }

    /**
     * @api {get} /seller/finish/remove Delete price.
     * @apiDescription Remove the paper finish from supported paper finishes.
     * @apiGroup Paper Finish
     * @apiParam {Long} fid Paper finish id.
     * @apiError (404) - Paper finish not found (invalid fid).
     * @apiError (403) - The paper finish is used in the card design.
     */
    @RequestMapping("/remove")
    public void Remove(@RequestParam long fid, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Seller seller = sellerDAO.get(id);

        FinishSeller fS = finishSellerDAO.get(fid, seller.getId());
        if (fS == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else if(!finishSellerDAO.canDelete(fS))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
            finishSellerDAO.delete(fS);
    }

    /**
     * @api {get} /seller/finish Seller paper finishes.
     * @apiDescription Get all the paper finishes supported by the seller.
     * @apiGroup Paper Finish
     * @apiSuccessExample {json} Example:-
     * [{
     *     "id": Paper finish id,
     *     "price" : Assigned price,
     *     "des": Description of the paper finish,
     *     "name": Paper finish name,
     *     "canDelete": True/False
     * }]
     */
    @RequestMapping("")
    public List<RFinishSeller> getFinish(@RequestAttribute String id) throws SQLException {
        Seller seller = sellerDAO.get(id);
        ArrayList<RFinishSeller> rFinishSellers = new ArrayList<>();

        List<FinishSeller> finishSellers = seller.getFinishes();
        for (FinishSeller fS : finishSellers) {
            Finish f = fS.getFinish();
            rFinishSellers.add(new RFinishSeller(fS.getPrice(), f.getFname(), f.getfDesc(), f.getFid(), finishSellerDAO.canDelete(fS)));
        }

        return rFinishSellers;
    }

    /**
     * @api {post} /seller/finish/request Request new finish.
     * @apiDescription Request admin to upload a new paper finish with the given description.
     * @apiGroup Paper Finish
     * @apiParam {String} name Finish name.
     * @apiParam {String} description Finish description.
     * @apiSuccess {Long} - Approval request id.
     */
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public Long request(@RequestAttribute String id, @RequestBody RFinish rf){
        Seller seller=sellerDAO.get(id);
        FinishApproval fA=new FinishApproval(seller, rf.getName(), rf.getDescription());
        finishApprovalDAO.save(fA);
        return fA.getId();
    }
}
