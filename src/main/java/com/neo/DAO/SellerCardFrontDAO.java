package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Card.SellerCardFront;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Praveen Gupta on 7/30/2017.
 */

@Service
@Transactional
public class SellerCardFrontDAO extends AbstractDAO<SellerCardFront, Long>{

    @Autowired
    public SellerCardFrontDAO(SessionFactory sessionFactory) {
        super(sessionFactory, SellerCardFront.class);
    }
}
