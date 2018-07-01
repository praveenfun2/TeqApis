package com.neo.Service;

import com.neo.DatabaseModel.Users.User;
import com.neo.Model.RUser;
import org.springframework.stereotype.Service;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

@Service
public class UserService {
    public void update(User u, RUser r) {
        if (isNotEmpty(r.getName())) u.setName(r.getName());
        if (isNotEmpty(r.getPhone())) u.setPhone(r.getPhone());
    }

    public boolean isValid(User u){
        return isNotEmpty(u.getName()) && isNotEmpty(u.getPhone());
    }
}
