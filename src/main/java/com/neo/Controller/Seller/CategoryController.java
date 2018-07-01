package com.neo.Controller.Seller;

import com.neo.DAO.SellerDAO;
import com.neo.DatabaseModel.Approval.CategoryApproval;
import com.neo.DatabaseModel.Approval.FinishApproval;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("sellerCategoryController")
@RequestMapping("/seller/category")
public class CategoryController {

    private final SellerDAO sellerDAO;

    @Autowired
    public CategoryController(SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    /**
     * @api {post} /seller/category/request Request new category.
     * @apiDescription Request admin to upload a new category with the given description.
     * @apiGroup Seller
     * @apiParam {String} name Category name.
     * @apiSuccess {Long} - Approval request id.
     */
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public Long request(@RequestAttribute String id, @RequestBody RCategory rc){
        Seller seller=sellerDAO.get(id);
        CategoryApproval categoryApproval=new CategoryApproval(seller, rc.getName());
        return categoryApproval.getId();
    }
}
