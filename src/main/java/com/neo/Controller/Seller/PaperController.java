package com.neo.Controller.Seller;

import com.neo.DAO.PaperApprovalDAO;
import com.neo.DAO.PaperDAO;
import com.neo.DAO.PaperSellerDAO;
import com.neo.DAO.SellerDAO;
import com.neo.DatabaseModel.Approval.PaperApproval;
import com.neo.DatabaseModel.Paper;
import com.neo.DatabaseModel.PaperSeller;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RPaper;
import com.neo.Model.RPaperSeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.neo.Utils.GeneralHelper.round2;

@RestController("sellerPaperController")
@RequestMapping("/seller/paper")
public class PaperController {

    private final PaperDAO paperDAO;
    private final PaperSellerDAO paperSellerDAO;
    private final SellerDAO sellerDAO;
    private final PaperApprovalDAO paperApprovalDAO;

    @Autowired
    public PaperController(PaperDAO paperDAO, PaperSellerDAO paperSellerDAO, SellerDAO sellerDAO, PaperApprovalDAO paperApprovalDAO) {
        this.paperDAO = paperDAO;
        this.paperSellerDAO = paperSellerDAO;
        this.sellerDAO = sellerDAO;
        this.paperApprovalDAO = paperApprovalDAO;
    }

    /**
     * @api {get} /seller/paper/new Price for paper quality.
     * @apiDescription Set the price for paper quality.
     * @apiGroup Paper Quality
     * @apiParam {Long} pqid Paper quality id.
     * @apiParam {Float} price Paper quality price.
     * @apiError (404) - Paper quality not found (invalid pqid).
     * @apiError (403) - User is forbidden.
     */

    @RequestMapping("/new")
    public void NewSellerPaper(@RequestParam long pqid, float price, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Seller seller = sellerDAO.get(id);
            Paper p = paperDAO.get(pqid);
            if (p == null)
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            else {
                PaperSeller paperSeller = paperSellerDAO.get(pqid, seller.getId());
                if (paperSeller != null) {
                    paperSeller.setPrice(round2(price));
                    paperSellerDAO.update(paperSeller);
                } else {
                    paperSeller = new PaperSeller(seller, p, round2(price));
                    paperSellerDAO.save(paperSeller);
                }
        }
    }

    /**
     * @api {get} /seller/paper/remove Delete price.
     * @apiDescription Remove the paper quality from supported paper qualities.
     * @apiGroup Paper Quality
     * @apiParam {Long} pqid Paper quality id.
     * @apiError (404) - Paper quality not found (invalid pqid).
     * @apiError (403) - The paper quality is used in the card design.
     */
    @RequestMapping("/remove")
    public void RemoveSellerPaper(@RequestParam long pqid, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Seller seller = sellerDAO.get(id);

        PaperSeller paperSeller = paperSellerDAO.get(pqid, seller.getId());
        if(paperSeller==null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else if(!paperSellerDAO.canDelete(paperSeller))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
            paperSellerDAO.delete(paperSeller);
    }

    /**
     * @api {get} /seller/paper Seller paper qualities.
     * @apiDescription Get all the paper qualities supported by the seller.
     * @apiGroup Paper Quality
     * @apiSuccessExample {json} Example:-
     * [{
     *     "id": Paper quality id,
     *     "price" : Assigned price,
     *     "des": Description of the paper quality,
     *     "name": Paper quality name,
     *     "canDelete": True/False
     * }]
     */
    @RequestMapping("")
    public List<RPaperSeller> getSellerPaper(@RequestAttribute String id) throws SQLException {
        Seller seller = sellerDAO.get(id);
        ArrayList<RPaperSeller> papers = new ArrayList<>();

        List<PaperSeller> paperSellers = seller.getPapers();
        for (PaperSeller paperSeller : paperSellers) {
            Paper paper = paperSeller.getPaper();
            papers.add(new RPaperSeller(paperSeller.getPrice(), paper.getName(), paper.getDes(), paper.getPqid(),
                    paperSellerDAO.canDelete(paperSeller)));
        }

        return papers;
    }

    /**
     * @api {post} /seller/paper/request Request new paper quality.
     * @apiDescription Request admin to upload a new paper quality with the given description.
     * @apiGroup Paper Quality
     * @apiParam {String} name Quality name.
     * @apiParam {String} description Quality description.
     * @apiSuccess {Long} - Approval request id.
     */
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public Long request(@RequestAttribute String id, @RequestBody RPaper rP){
        Seller seller=sellerDAO.get(id);
        PaperApproval paperApproval=new PaperApproval(seller, rP.getName(), rP.getDescription());
        paperApprovalDAO.save(paperApproval);
        return paperApproval.getId();
    }
}
