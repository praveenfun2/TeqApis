package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Card.Card;
import com.neo.DatabaseModel.Card.SellerCard;
import com.neo.DatabaseModel.Promotion;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Praveen Gupta on 7/30/2017.
 */

@Service
@Transactional
public class SellerCardDAO extends AbstractDAO<SellerCard, Long>{

    @Autowired
    public SellerCardDAO(SessionFactory sessionFactory) {
        super(sessionFactory, SellerCard.class);
    }

    @Override
    public SellerCard get(Long aLong) {
        Criteria criteria=criteria();
        criteria.add(Restrictions.or(Restrictions.eq("type", Card.SELLER_CARD_FRONT), Restrictions.eq("type", Card.SELLER_CARD_BACK)));
        return getOneByCriteria(criteria);
    }

    public float getPrice(SellerCard card){
        Promotion promotion=card.getPromotion();
        if(promotion==null) return card.getPrice();
        return card.getPrice()-promotion.getDiscount();
    }

    public float getPrice(Long id){
       return getPrice(get(id));
    }

}
