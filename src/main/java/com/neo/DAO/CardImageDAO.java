package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.CardImage;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CardImageDAO extends AbstractDAO<CardImage, Long> {

    @Autowired
    public CardImageDAO(SessionFactory sessionFactory) {
        super(sessionFactory, CardImage.class);
    }
}
