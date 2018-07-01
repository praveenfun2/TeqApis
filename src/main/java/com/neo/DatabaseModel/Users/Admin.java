package com.neo.DatabaseModel.Users;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = User.ADMIN_TYPE)
public class Admin extends User {

    public Admin() {
    }

    public Admin(String id, String pass) {
        this.id=id;
        this.pass=pass;
    }
}
