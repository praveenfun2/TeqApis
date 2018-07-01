package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.PaymentOrder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 17/6/17.
 */

@Service
@Transactional
public class PaymentOrderDAO extends AbstractDAO<PaymentOrder, Long> {

    @Autowired
    public PaymentOrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory, PaymentOrder.class);
    }
}
