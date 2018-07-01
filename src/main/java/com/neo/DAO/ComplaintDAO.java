package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Message.Complaint;
import com.neo.DatabaseModel.Users.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by localadmin on 11/7/17.
 */

@Service
@Transactional
public class ComplaintDAO extends AbstractDAO<Complaint, Long> {

    private final int MAX_RESULT=5;

    @Autowired
    public ComplaintDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Complaint.class);
    }

    public List<Complaint> getChats(User user){
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("sender.id", user.getId()));
        criteria.setMaxResults(MAX_RESULT);
        criteria.addOrder(Order.desc("timestamp"));
        return criteria.list();
    }

}
