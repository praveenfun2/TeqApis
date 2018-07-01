package com.neo.Controller.Seller;

import com.neo.DAO.CategoryDAO;
import com.neo.DAO.SellerDAO;
import com.neo.DAO.SubCategoryApprovalDAO;
import com.neo.DatabaseModel.Approval.SubCategoryApproval;
import com.neo.DatabaseModel.Category;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RCategory;
import com.neo.Model.RSubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController("sellerSubCategoryController")
@RequestMapping("/seller/subcategory")
public class SubCategoryController {
    private final SellerDAO sellerDAO;
    private final CategoryDAO categoryDAO;
    private final SubCategoryApprovalDAO subCategoryApprovalDAO;

    @Autowired
    public SubCategoryController(SellerDAO sellerDAO, CategoryDAO categoryDAO, SubCategoryApprovalDAO subCategoryApprovalDAO) {
        this.sellerDAO = sellerDAO;
        this.categoryDAO = categoryDAO;
        this.subCategoryApprovalDAO = subCategoryApprovalDAO;
    }

    /**
     * @api {post} /seller/subcategory/request New sub category.
     * @apiDescription Request admin to upload a new sub category with the given description.
     * @apiGroup Seller
     * @apiParamExample {json} Request:- {
     *     "name": Sub category name,
     *     "category":{
     *         "id": Category id under which this sub category should be placed.
     *     }
     * }
     * @apiSuccess {Long} - Approval request id.
     * @apiError (404) - Invalid category id.
     */
    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public Long request(@RequestAttribute String id, @RequestBody RSubCategory rs, HttpServletResponse response) {
        Seller seller = sellerDAO.get(id);

        RCategory rCategory = rs.getCategory();
        Category category;
        if (rCategory == null || (category = categoryDAO.get(rCategory.getId())) == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        SubCategoryApproval subCategoryApproval = new SubCategoryApproval(seller, rs.getName(), category);
        subCategoryApprovalDAO.save(subCategoryApproval);
        return subCategoryApproval.getId();
    }
}
