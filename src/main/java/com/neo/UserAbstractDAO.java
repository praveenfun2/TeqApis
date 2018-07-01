package com.neo;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by localadmin on 18/6/17.
 */

@Transactional
public class UserAbstractDAO<E, ID extends Serializable>  extends AbstractDAO<E, ID>{

    public UserAbstractDAO(SessionFactory sessionFactory, Class<?> aClass) {
        super(sessionFactory, aClass);
    }

    /*@Override
    public E get(ID id) {
        Criteria criteria=criteria();
        criteria.add(Restrictions.and(Restrictions.idEq(id)));
        List<E> list=criteria.list();
        return list.size()>0? list.get(0):null;
    }

    @Override
    public E getOneByCriteria(Criteria criteria) {
        criteria.add(Restrictions.eq("otp", 1));
        return super.getOneByCriteria(criteria);
    }

    @Override
    public List<E> listAll() {
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("otp", 1));
        return criteria.list();
    }

    @Override
    public List<E> list(Criteria criteria) {
        criteria.add(Restrictions.eq("otp", 1));
        return super.list(criteria);
    }*/
}
