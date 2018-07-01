package com.neo.Controller.Admin;

import com.neo.DAO.UserDAO;
import com.neo.DatabaseModel.Users.User;
import com.neo.Model.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController("adminUserController")
@RequestMapping("/admin/user")
public class UserController {

    private final UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @RequestMapping("")
    public ArrayList<RUser> getAllUser() throws SQLException {
        ArrayList<RUser> rUsers = new ArrayList<>();

        List<User> users = userDAO.listAll();
        for (User user : users) {
            RUser rUser = new RUser(user.getId());
            rUser.setType(user.getType());
            rUser.setActivated(user.getOtp() == 1);
            rUsers.add(rUser);
        }

        return rUsers;

    }


    @RequestMapping("/activate")
    public void activateUser(@RequestParam String uid) {
        User user = userDAO.get(uid);
        user.setOtp(1);
        userDAO.update(user);
    }

    @RequestMapping("/deactivate")
    public void deActivateUser(@RequestParam String uid) {
        User user = userDAO.get(uid);
        user.setOtp(-1);
        userDAO.update(user);
    }

    @RequestMapping("/remove")
    public void removeUser(@RequestParam String id) {
        userDAO.delete(id);
    }
}
