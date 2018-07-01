package com.neo.Controller.Admin;

import com.neo.DAO.*;
import com.neo.DatabaseModel.*;
import com.neo.DatabaseModel.Approval.Approval;
import com.neo.DatabaseModel.Approval.FinishApproval;
import com.neo.DatabaseModel.Approval.PaperApproval;
import com.neo.DatabaseModel.Approval.SubCategoryApproval;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/approval")
public class ApprovalController {

    private final ApprovalDAO approvalDAO;
    private final NotificationDAO notificationDAO;
    private final CategoryDAO categoryDAO;
    private final SubCategoryDAO subCategoryDAO;
    private final PaperDAO paperDAO;
    private final FinishDAO finishDAO;
    private final SubCategoryApprovalDAO subCategoryApprovalDAO;
    private final PaperApprovalDAO paperApprovalDAO;
    private final FinishApprovalDAO finishApprovalDAO;

    @Autowired
    public ApprovalController(ApprovalDAO approvalDAO, NotificationDAO notificationDAO, CategoryDAO categoryDAO, SubCategoryDAO subCategoryDAO, PaperDAO paperDAO, FinishDAO finishDAO, SubCategoryApprovalDAO subCategoryApprovalDAO, PaperApprovalDAO paperApprovalDAO, FinishApprovalDAO finishApprovalDAO) {
        this.approvalDAO = approvalDAO;
        this.notificationDAO = notificationDAO;
        this.categoryDAO = categoryDAO;
        this.subCategoryDAO = subCategoryDAO;
        this.paperDAO = paperDAO;
        this.finishDAO = finishDAO;
        this.subCategoryApprovalDAO = subCategoryApprovalDAO;
        this.paperApprovalDAO = paperApprovalDAO;
        this.finishApprovalDAO = finishApprovalDAO;
    }


    /**
     * @api {get} /admin/approval Approvals.
     * @apiDescription List of all types of approval requests.
     * @apiGroup Admin
     * @apiSuccessExample {json} Response:-  [
     *  {
     *      "id": Approval id,
     *      "sid": seller id who made the request,
     *      "name": category/subcategory/paper quality/paper finish name,
     *      "desc": paper quality/paper finish description,
     *      "cat": Category name in case this request is a subcategory type approval request,
     *      "type": 1-> category; 2-> sub category; 3->Paper quality, 4-> Paper Finish.
     *  }
     * ]
     */
    @RequestMapping("")
    public List<RApproval> getApprovalRequests() {

        List<Approval> approvals = approvalDAO.listAll();
        List<RApproval> rApprovals = new ArrayList<>();

        for (Approval approval : approvals) {
            RApproval rApproval = new RApproval(approval.getId(), approval.getSeller().getId(), approval.getName(), approval.getType());

            switch (approval.getType()) {
                case Approval.SUB_CATEGORY:
                    SubCategoryApproval subCategoryApproval=subCategoryApprovalDAO.get(approval.getId());
                    rApproval.setCat(subCategoryApproval.getCategory().getName());
                    break;
                case Approval.FINISH:
                    FinishApproval finishApproval=finishApprovalDAO.get(approval.getId());
                    rApproval.setDesc(finishApproval.getDescription());
                    break;
                case Approval.PAPER:
                    PaperApproval paperApproval=paperApprovalDAO.get(approval.getId());
                    rApproval.setDesc(paperApproval.getDescription());
                    break;
            }
            rApprovals.add(rApproval);
        }

        return rApprovals;
    }

    /**
     * @api {get} /admin/reject Reject approval.
     * @apiDescription Reject approval request.
     * @apiGroup Admin
     * @apiParam  {Long} id Approval id.
     */
    @RequestMapping("/reject")
    public void deleteApproval(@RequestParam long id) {
        Approval approval = approvalDAO.get(id);
        if (approval == null) return ;

        Seller seller = approval.getSeller();
        switch (approval.getType()) {
            case Approval.CATEGORY:
                notificationDAO.addNotification(Notification.TYPE_SELLER_APPROVAL,
                        Notification.MESSAGE_SELLER_CATEGORY_REQUEST_REJECTED, seller);
                break;
            case Approval.FINISH:
                notificationDAO.addNotification(Notification.TYPE_SELLER_APPROVAL,
                        Notification.MESSAGE_SELLER_FINISH_REQUEST_REJECTED, seller);
                break;
            case Approval.PAPER:
                notificationDAO.addNotification(Notification.TYPE_SELLER_APPROVAL,
                        Notification.MESSAGE_SELLER_PAPER_REQUEST_REJECTED, seller);
                break;
            case Approval.SUB_CATEGORY:
                notificationDAO.addNotification(Notification.TYPE_SELLER_APPROVAL,
                        Notification.MESSAGE_SELLER_SUB_CATEGORY_REQUEST_REJECTED, seller);
                break;
        }
        approvalDAO.delete(approval);
    }


    /**
     * @api {get} /admin/approve Approve.
     * @apiDescription Approve request.
     * @apiGroup Admin
     * @apiParam  {Long} id Approval id.
     */
    @RequestMapping("/approve")
    public void approve(@RequestParam long id) {
        Approval approval = approvalDAO.get(id);
        if (approval == null) return ;

        Seller seller = approval.getSeller();
        switch (approval.getType()) {
            case Approval.CATEGORY: {
                Category category = new Category(approval.getName());
                categoryDAO.save(category);
                notificationDAO.addNotification(Notification.TYPE_SELLER_APPROVAL,
                        Notification.MESSAGE_SELLER_CATEGORY_REQUEST_ACCEPTED, seller);
                break;
            }
            case Approval.SUB_CATEGORY: {
                SubCategoryApproval subCategoryApproval=subCategoryApprovalDAO.get(approval.getId());
                SubCategory subCategory = new SubCategory(subCategoryApproval.getName(), subCategoryApproval.getCategory());
                subCategoryDAO.save(subCategory);
                notificationDAO.addNotification(Notification.TYPE_SELLER_APPROVAL,
                        Notification.MESSAGE_SELLER_SUB_CATEGORY_REQUEST_ACCEPTED, seller);
                break;
            }
            case Approval.PAPER: {
                PaperApproval paperApproval=paperApprovalDAO.get(approval.getId());
                Paper paper = new Paper(paperApproval.getName(), paperApproval.getDescription());
                paperDAO.save(paper);
                notificationDAO.addNotification(Notification.TYPE_SELLER_APPROVAL,
                        Notification.MESSAGE_SELLER_PAPER_REQUEST_ACCEPTED, seller);
                break;
            }
            case Approval.FINISH: {
                FinishApproval finishApproval=finishApprovalDAO.get(approval.getId());
                Finish finish = new Finish(finishApproval.getName(), finishApproval.getDescription());
                finishDAO.save(finish);
                notificationDAO.addNotification(Notification.TYPE_SELLER_APPROVAL,
                        Notification.MESSAGE_SELLER_FINISH_REQUEST_ACCEPTED, seller);
                break;
            }
        }
        approvalDAO.delete(approval);
    }
}
