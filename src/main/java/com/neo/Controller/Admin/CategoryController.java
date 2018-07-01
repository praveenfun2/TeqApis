package com.neo.Controller.Admin;

import com.neo.DAO.CategoryDAO;
import com.neo.DatabaseModel.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestController("adminCategoryController")
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryDAO categoryDAO;

    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @RequestMapping("/new")
    public void NewCategory(@RequestParam String name, HttpServletResponse response) throws SQLException {
        Category category = new Category(name);
        if (!categoryDAO.save(category))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @RequestMapping("/update")
    public void NewCategory(@RequestParam String name, long id, HttpServletResponse response) throws SQLException {
        Category category = categoryDAO.get(id);
        if (category == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else {
            category.setName(name);
            if (categoryDAO.update(category) != null)
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @RequestMapping(value = "/remove")
    public void removeCat(@RequestParam long id, HttpServletResponse response) {
        Category category = categoryDAO.get(id);
        if (category == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else if (!categoryDAO.delete(category))
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

}
