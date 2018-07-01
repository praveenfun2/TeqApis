package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Shipment.UserDeliveryType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourierDeliveryTypeDAO extends AbstractDAO<UserDeliveryType, Long> {

    @Autowired
    public CourierDeliveryTypeDAO(SessionFactory sessionFactory) {
        super(sessionFactory, UserDeliveryType.class);
    }
}
