package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Users.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 16/6/17.
 */

@Service
@Transactional
public class AddressDAO extends AbstractDAO<Address, Long> {

    @Autowired
    public AddressDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Address.class);
    }
}
