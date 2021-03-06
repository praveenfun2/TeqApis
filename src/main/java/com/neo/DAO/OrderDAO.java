package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.Order.DesignerOrder;
import com.neo.DatabaseModel.Order.Orders;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 11/7/17.
 */

@Service
@Transactional
public class OrderDAO extends AbstractDAO<Orders, Long> {

    @Autowired
    public OrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Orders.class);
    }

}
