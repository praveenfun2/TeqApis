package com.neo.Service;

import com.neo.DatabaseModel.Users.Designer;
import com.neo.Model.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

@Service
public class DesignerService {

    private final UserService userService;

    @Autowired
    public DesignerService(UserService userService) {
        this.userService = userService;
    }


    public void update(Designer d, RUser r) {
        userService.update(d, r);
        if(isNotEmpty(r.getPaypal()))
            d.setPaypal(r.getPaypal());
    }

    public boolean isValid(Designer d) {
        return userService.isValid(d) && isNotEmpty(d.getPaypal());
    }
}
