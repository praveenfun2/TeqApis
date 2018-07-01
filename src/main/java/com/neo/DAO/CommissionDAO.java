package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Commission;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by localadmin on 17/6/17.
 */

@Service
@Transactional
public class CommissionDAO extends AbstractDAO<Commission, Integer> {

    @Autowired
    public CommissionDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Commission.class);
    }

    @Override
    public List<Commission> listAll() {
        Criteria criteria=criteria();
        criteria.addOrder(Order.asc("type"));
        return criteria.list();
    }
}
