package com.neo.DAO;

import com.neo.DatabaseModel.Users.Customer;
import com.neo.UserAbstractDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 18/6/17.
 */

@Service
@Transactional
public class CustomerDAO extends UserAbstractDAO<Customer, String> {

    @Autowired
    public CustomerDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Customer.class);
    }

}
