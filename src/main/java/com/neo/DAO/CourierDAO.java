package com.neo.DAO;

import com.neo.DatabaseModel.Users.Courier;
import com.neo.UserAbstractDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Transactional
public class CourierDAO extends UserAbstractDAO<Courier, String> {

    @Autowired
    public CourierDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Courier.class);
    }

    /*public boolean verified(Courier courier){
        return courier.getLat()!=null && courier.getLon()!=null&& courier.getAddress()!=null;
    }*/



}
