package com.neo.Controller;

import com.neo.DAO.CategoryDAO;
import com.neo.DatabaseModel.Category;
import com.neo.DatabaseModel.SubCategory;
import com.neo.Model.RSubCategory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController("/subcategory")
public class SubCategoryController {

    private final CategoryDAO categoryDAO;

    public SubCategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    /*@RequestMapping("")
    public List<RSubCategory> getCategories(@RequestParam(required = false) ArrayList<Long> catids) {

        List<RSubCategory> rSubCategories = new ArrayList<>();

        if (catids == null) {
            List<SubCategory> subCategories = subCategoryDAO.listAll();
            for (SubCategory subCategory : subCategories)
                rSubCategories.add(new RSubCategory(subCategory.getSubid(), subCategory.getName()));
        } else {
            for (long id : catids) {
                List<SubCategory> subCategories = categoryDAO.get(id).getSubCategories();
                for (SubCategory subCategory : subCategories)
                    rSubCategories.add(new RSubCategory(subCategory.getSubid(), subCategory.getName()));
            }
        }

        return rSubCategories;
    }*/

    /**
     * @api {get} /subcategory Sub-categories
     * @apiGroup Category
     * @apiDescription Get the sub-categories of the category identified by "catid".
     * @apiParam {Long} catid Category id
     * @apiError (404) - Category not found/wrong id.
     * @apiSuccessExample {json} Response
     * [{
     *     "id": sub-category id,
     *     "name": sub-category name
     * }]
     * */
    @RequestMapping("")
    public List<RSubCategory> getSubCategories(@RequestParam long catid, HttpServletResponse response) throws SQLException {

        Category category = categoryDAO.get(catid);
        if (category == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        List<RSubCategory> rSubCategories = new ArrayList<>();
        for (SubCategory subCategory : category.getSubCategories())
            rSubCategories.add(new RSubCategory(subCategory.getSubid(), subCategory.getName()));

        return rSubCategories;
    }
}
