package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Ticket;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 11/7/17.
 */

@Service
@Transactional
public class TicketDAO extends AbstractDAO<Ticket, Long>{

    @Autowired
    public TicketDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Ticket.class);
    }

    public Ticket getByOrder(Long orderId){
        if(orderId==null) return null;
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("order.id", orderId));
        criteria.add(Restrictions.eq("resolved", false));
        return getOneByCriteria(criteria);
    }
}
