package com.neo.DAO;

import com.neo.DatabaseModel.Users.Seller;
import com.neo.UserAbstractDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 15/6/17.
 */

@Service
@Transactional
public class SellerDAO extends UserAbstractDAO<Seller, String> {

    @Autowired
    public SellerDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Seller.class);
    }

    public boolean verified(Seller seller) {
        return seller.getAddress() != null && seller.getAddress().getLat() != null && seller.getAddress().getLng() != null && seller.getPrice() != null;
    }
}
