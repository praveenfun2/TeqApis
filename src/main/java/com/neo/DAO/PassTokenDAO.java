package com.neo.DAO;


import com.neo.AbstractDAO;
import com.neo.DatabaseModel.PassToken;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PassTokenDAO extends AbstractDAO<PassToken, String>{

    @Autowired
    public PassTokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory, PassToken.class);
    }
}
