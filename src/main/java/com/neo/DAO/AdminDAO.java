package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Users.Admin;
import com.neo.Utils.GeneralHelper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AdminDAO extends AbstractDAO<Admin, String> {

    @Autowired
    public AdminDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Admin.class);
    }

}
