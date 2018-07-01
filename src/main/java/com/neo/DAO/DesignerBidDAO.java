package com.neo.DAO;


import com.neo.AbstractDAO;
import com.neo.DatabaseModel.DesignerBid;
import com.neo.DatabaseModel.DesignerOffer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 14/6/17.
 */

@Service
@Transactional
public class DesignerBidDAO extends AbstractDAO<DesignerBid, Long>{

    @Autowired
    public DesignerBidDAO(SessionFactory sessionFactory) {
        super(sessionFactory, DesignerBid.class);
    }
}
