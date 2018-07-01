package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.API;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Access;

/**
 * Created by localadmin on 29/6/17.
 */

@Service
@Transactional
public class APIDAO extends AbstractDAO<API, String> {

    @Autowired
    public APIDAO(SessionFactory sessionFactory) {
        super(sessionFactory, API.class);
    }

    public API paypalApi(){
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("type", API.PAYPAL));
        return getOneByCriteria(criteria);
    }

    public API geocodingApi(){
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("type", API.GEOCODING));
        return getOneByCriteria(criteria);
    }

    public API emailApi(){
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("type", API.EMAIL));
        return getOneByCriteria(criteria);
    }
}
