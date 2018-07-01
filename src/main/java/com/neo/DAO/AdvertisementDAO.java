package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Advertisement;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 17/6/17.
 */

@Service
@Transactional
public class AdvertisementDAO extends AbstractDAO<Advertisement, Integer> {

    @Autowired
    public AdvertisementDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Advertisement.class);
    }

    public float getAdPrice(int type){
        Advertisement advertisement=get(type);
        if(advertisement==null) return -1;
        return advertisement.getAmt();
    }
}
