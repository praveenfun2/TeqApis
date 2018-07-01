package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Users.User;
import com.neo.Utils.GeneralHelper;
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
public class UserDAO extends AbstractDAO<User, String> {

    @Autowired
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    @Override
    public List<User> listAll() {
        Criteria criteria=criteria();
        criteria.add(Restrictions.ne("type", User.ADMIN_TYPE));
        return list(criteria);
    }

    /*@Override
    public Criteria criteria() {
        Criteria criteria=criteria();
        criteria.add(Restrictions.isNotNull("type"));
        return criteria;
    }*/
}
