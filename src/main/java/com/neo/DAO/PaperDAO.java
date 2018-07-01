package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Paper;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 15/6/17.
 */

@Service
@Transactional
public class PaperDAO extends AbstractDAO<Paper, Long> {

    @Autowired
    public PaperDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Paper.class);
    }

    public Paper getByName(String name){
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("name", name));
        return list(criteria).get(0);
    }

}
