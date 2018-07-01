package com.neo.DAO;

import com.neo.DatabaseModel.Users.Designer;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.UserAbstractDAO;
import org.bouncycastle.jcajce.provider.symmetric.DES;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 18/6/17.
 */

@Service
@Transactional
public class DesignerDAO extends UserAbstractDAO<Designer, String> {

    @Autowired
    public DesignerDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Designer.class);
    }

    public boolean verified(Designer designer){
        return designer.getAddress()!=null && designer.getPaypal()!=null;
    }
}
