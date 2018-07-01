package com.neo.Controller;

import com.neo.Constants;
import com.neo.DAO.MessageDAO;
import com.neo.DAO.OrderDAO;
import com.neo.DAO.UserDAO;
import com.neo.DatabaseModel.Message.Complaint;
import com.neo.DatabaseModel.Message.Message;
import com.neo.DatabaseModel.Order.Orders;
import com.neo.DatabaseModel.Users.User;
import com.neo.Model.RMessage;
import com.neo.Model.RMessages;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(Constants.FRONT_END)
public class MessageController {

    private final MessageDAO messageDAO;
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final SimpleDateFormat simpleDateFormat;

    @Autowired
    public MessageController(MessageDAO messageDAO, UserDAO userDAO, OrderDAO orderDAO) {
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public boolean message(@RequestHeader("Authorization") String token, @RequestBody RMessage rMessage){
        User user = userDAO.get(JWTHelper.decodeToken(token).getId());
        if (user == null) return false;

        Orders order=orderDAO.get(rMessage.getOrderId());
        if(order==null) return false;

        Message message=new Message(rMessage.getMessage(), user, order);
        messageDAO.save(message);

        return true;
    }

    @RequestMapping("/getMessages")
    public RMessages getMessages(@RequestHeader("Authorization") String token, Long orderId) {
        RMessages rMessages=new RMessages();

        User user = userDAO.get(JWTHelper.decodeToken(token).getId());
        if (user == null) return rMessages;

        Orders order=orderDAO.get(orderId);
        if(order==null) return rMessages;

        rMessages.setOrderId(orderId);

        List<Message> messages = order.getMessages();
        for (Message message : messages) {
            User sender = message.getSender();
            String time=simpleDateFormat.format(new Date(message.getTimestamp()));
            rMessages.addMessage(message.getMessage(), sender.getId().equals(user.getId())? "You: ": sender.getName()+": ", time);
        }

        return rMessages;
    }
}
