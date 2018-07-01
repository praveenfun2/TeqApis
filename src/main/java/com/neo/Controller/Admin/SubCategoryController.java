package com.neo.Controller.Admin;

import com.neo.DAO.CategoryDAO;
import com.neo.DAO.SubCategoryDAO;
import com.neo.DatabaseModel.Category;
import com.neo.DatabaseModel.SubCategory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController("adminSubCategoryController")
@RequestMapping("/admin/subcategory")
public class SubCategoryController {
    private final SubCategoryDAO subCategoryDAO;
    private final CategoryDAO categoryDAO;

    public SubCategoryController(SubCategoryDAO subCategoryDAO, CategoryDAO categoryDAO) {
        this.subCategoryDAO = subCategoryDAO;
        this.categoryDAO = categoryDAO;
    }

    @RequestMapping("/new")
    public void New(@RequestParam long catid, String name, HttpServletResponse response) {
        Category category = categoryDAO.get(catid);
        if (category == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else {
            SubCategory subCategory = new SubCategory(name, category);
            if (!subCategoryDAO.save(subCategory))
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @RequestMapping("/update")
    public void Update(@RequestParam long id, String name, HttpServletResponse response) {
        SubCategory subCategory = subCategoryDAO.get(id);
        if (subCategory == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else {
            subCategory.setName(name);
            if (subCategoryDAO.update(subCategory) == null)
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @RequestMapping("/remove")
    public void remove(@RequestParam long id, HttpServletResponse response){
        SubCategory subCategory = subCategoryDAO.get(id);
        if (subCategory == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else if(!subCategoryDAO.delete(subCategory))
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }
}
