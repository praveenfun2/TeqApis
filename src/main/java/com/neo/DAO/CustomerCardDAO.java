package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Card.CustomerCard;
import com.neo.DatabaseModel.Order.CardOrder;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Praveen Gupta on 7/30/2017.
 */

@Service
@Transactional
public class CustomerCardDAO extends AbstractDAO<CustomerCard, Long> {

    @Autowired
    public CustomerCardDAO(SessionFactory sessionFactory) {
        super(sessionFactory, CustomerCard.class);
    }
}
