package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.CardsSubcat;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 16/6/17.
 */

@Service
@Transactional
public class CardsSubcatDAO extends AbstractDAO<CardsSubcat, Long> {

    @Autowired
    public CardsSubcatDAO(SessionFactory sessionFactory) {
        super(sessionFactory, CardsSubcat.class);
    }
}
