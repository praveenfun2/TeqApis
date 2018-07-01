package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Category;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by localadmin on 16/6/17.
 */

@Service
@Transactional
public class CategoryDAO extends AbstractDAO<Category, Long> {

    @Autowired
    public CategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Category.class);
    }

    public Category getByName(String name){
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("name", name));
        List<Category> categories=list(criteria);

        if(categories.size()==0) return null;
        return categories.get(0);
    }
}
