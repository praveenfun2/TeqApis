package com.neo;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by localadmin on 15/6/17.
 */

@Transactional
public class AbstractDAO<E, ID extends Serializable> {

    private SessionFactory sessionFactory;
    private Class<?> aClass;

    public AbstractDAO(SessionFactory sessionFactory, Class<?> aClass){
        this.sessionFactory=sessionFactory;
        this.aClass=aClass;
    }

    public Session currentSession(){
        return sessionFactory.getCurrentSession();
    }

    public Criteria criteria(){
        return currentSession().createCriteria(aClass);
    }

    public E get(ID id){
        List<E> list=criteria().add(Restrictions.idEq(id)).list();
        return list.size()>0? list.get(0):null;
    }

    public E getOneByCriteria(Criteria criteria){
        List<E> list=criteria.list();
        return list.size()>0? list.get(0):null;
    }

    public List<E> listAll(){
        return criteria().list();
    }

    public boolean save(E e){
        try {
            currentSession().persist(e);
            //currentSession().flush();
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public E update(E e){
        try {
            return (E) currentSession().merge(e);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean delete(E e){
        try {
            currentSession().delete(e);
            return true;
        }
        catch (Exception e1){
            System.out.println(e1.getMessage());
            return false;
        }
    }

    public boolean delete(ID id){
        return delete(get(id));
    }

    public List<E> list(Criteria criteria){
        return criteria.list();
    }
}
