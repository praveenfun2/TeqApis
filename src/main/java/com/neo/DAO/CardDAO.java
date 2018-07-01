package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Card.Card;
import com.neo.DatabaseModel.Card.SellerCard;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 16/6/17.
 */
@Service
@Transactional
public class CardDAO extends AbstractDAO<Card, Long> {

    @Autowired
    public CardDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Card.class);
    }
}
