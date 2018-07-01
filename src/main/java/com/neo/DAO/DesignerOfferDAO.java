package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.DesignerOffer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by localadmin on 14/6/17.
 */

@Service
@Transactional
public class DesignerOfferDAO extends AbstractDAO<DesignerOffer, Long>{

    @Autowired
    public DesignerOfferDAO(SessionFactory sessionFactory) {
        super(sessionFactory, DesignerOffer.class);
    }

    @Override
    public List<DesignerOffer> listAll() {
        Criteria criteria=criteria();
        criteria.add(Restrictions.isNotNull("customer"));
        return criteria.list();
    }
}
