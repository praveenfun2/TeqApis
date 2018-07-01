package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Card.SellerCard;
import com.neo.DatabaseModel.Card.SellerCardBack;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Praveen Gupta on 7/30/2017.
 */

@Service
@Transactional
public class SellerCardBackDAO extends AbstractDAO<SellerCardBack, Long>{

    @Autowired
    public SellerCardBackDAO(SessionFactory sessionFactory) {
        super(sessionFactory, SellerCardBack.class);
    }
}
