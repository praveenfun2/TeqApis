package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Message.Chat;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by localadmin on 16/7/17.
 */

@Service
@Transactional
public class ChatDAO extends AbstractDAO<Chat, Long> {

    @Autowired
    public ChatDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Chat.class);
    }

    public List<Chat> getUnread(String uid){
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("read", false));
        criteria.add(Restrictions.eq("receiver.id", uid));
        criteria.addOrder(Order.desc("timestamp"));
        return criteria.list();
    }

    public void markRead(String senderId, String receiverId){
        Criteria criteria=criteria();
        criteria.add(Restrictions.eq("sender.id", senderId));
        criteria.add(Restrictions.eq("receiver.id", receiverId));
        criteria.add(Restrictions.lt("timestamp", new Date().getTime()));
        criteria.add(Restrictions.eq("read", false));

        List<Chat> chats=criteria.list();
        for(Chat chat1: chats){
            chat1.setRead(true);
            update(chat1);
        }
    }
}
