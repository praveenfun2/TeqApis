package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Notification;
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
 * Created by localadmin on 24/6/17.
 */
@Service
@Transactional
public class NotificationDAO extends AbstractDAO<Notification, Long> {

    @Autowired
    public NotificationDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Notification.class);
    }

    public List<Notification> getNotifications(User user){
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("read", false));
        criteria.setMaxResults(5);
        criteria.addOrder(Order.desc("timestamp"));
        criteria.add(Restrictions.eq("user.id", user.getId()));
        return criteria.list();
    }

    public void addNotification(String type, String message, User user){
        Notification notification=new Notification(message, type, user);
        save(notification);
    }
}

