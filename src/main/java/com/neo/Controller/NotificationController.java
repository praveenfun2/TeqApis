package com.neo.Controller;

import com.neo.Constants;
import com.neo.DAO.*;
import com.neo.DatabaseModel.Notification;
import com.neo.DatabaseModel.Users.User;
import com.neo.Model.RNotification;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localadmin on 22/6/17.
 */

@RestController
@CrossOrigin(Constants.FRONT_END)
public class NotificationController {

    private final CustomerDAO customerDAO;
    private final UserDAO userDAO;
    private final SellerDAO sellerDAO;
    private final DesignerDAO designerDAO;
    private final NotificationDAO notificationDAO;

    @Autowired
    public NotificationController(CustomerDAO customerDAO, UserDAO userDAO, SellerDAO sellerDAO, DesignerDAO designerDAO, NotificationDAO notificationDAO) {
        this.customerDAO = customerDAO;
        this.userDAO = userDAO;
        this.sellerDAO = sellerDAO;
        this.designerDAO = designerDAO;
        this.notificationDAO = notificationDAO;
    }

    @RequestMapping("/getNotifications")
    public List<RNotification> getNotifications(@RequestHeader("Authorization") String token){
        List<RNotification> rNotifications=new ArrayList<>();

        User user=userDAO.get(JWTHelper.decodeToken(token).getId());
        if(user==null) return rNotifications;
        
        List<Notification> notifications = notificationDAO.getNotifications(user);
        for(Notification notification: notifications)
            rNotifications.add(new RNotification(notification.getType(),
                    notification.getMessage(), notification.getId()));
        return rNotifications;
    }

    @RequestMapping("/readNotification")
    public boolean read(@RequestHeader("Authorization") String token, Long id){
        User user=userDAO.get(JWTHelper.decodeToken(token).getId());
        if(user==null) return false;

        Notification notification=notificationDAO.get(id);
        if(notification==null) return false;

        notification.setRead(true);
        notificationDAO.update(notification);

        return true;
    }

}
