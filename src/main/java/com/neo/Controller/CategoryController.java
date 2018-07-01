package com.neo.Controller;

import com.neo.DAO.CategoryDAO;
import com.neo.DatabaseModel.Category;
import com.neo.DatabaseModel.SubCategory;
import com.neo.Model.RCategory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryDAO categoryDAO;

    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    /**
     * @api {get} /category Category & sub-category
     * @apiDescription Category & sub-category list.
     * @apiGroup Category
     * @apiParam {Long} [id] category id - to obtain sub categories of a particular category. If not provided, returns all categories.
     * @apiSuccessExample {json} Response
     * [{
     * "id": "category id",
     * "name": "category name",
     * "subCategories":[{
     * "id": "sub-category id",
     * "name": "sub-category name"
     * }]
     * }]
     * @apiError (404) - Category with the given id (if provided) is not found
     */
    @RequestMapping("")
    public List<RCategory> getCategory(@RequestParam(required = false) Long id, HttpServletResponse response) throws SQLException {
        List<Category> categories;
        if (id != null) {
            Category c = categoryDAO.get(id);
            if (c == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            categories = new ArrayList<>();
            categories.add(c);
        } else categories = categoryDAO.listAll();

        List<RCategory> rCategories = new ArrayList<>();
        for (Category category : categories) {
            RCategory rCategory = new RCategory(category.getCatid(), category.getName());
            List<SubCategory> subCategories = category.getSubCategories();
            for (SubCategory subCategory : subCategories)
                rCategory.addSub(subCategory.getSubid(), subCategory.getName());
            rCategories.add(rCategory);
        }
        return rCategories;
    }

}
