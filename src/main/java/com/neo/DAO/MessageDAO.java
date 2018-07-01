package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Message.Message;
import com.neo.DatabaseModel.Order.Orders;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageDAO extends AbstractDAO<Message, Long> {

    @Autowired
    public MessageDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Message.class);
    }

}
