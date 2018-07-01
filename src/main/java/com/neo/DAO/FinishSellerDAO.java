package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.FinishSeller;
import com.neo.DatabaseModel.PaperSeller;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by localadmin on 16/6/17.
 */

@Service
@Transactional
public class FinishSellerDAO extends AbstractDAO<FinishSeller, Long>{

    private final CardsFinishDAO cardsFinishDAO;

    @Autowired
    public FinishSellerDAO(SessionFactory sessionFactory, CardsFinishDAO cardsFinishDAO) {
        super(sessionFactory, FinishSeller.class);
        this.cardsFinishDAO = cardsFinishDAO;
    }

    public FinishSeller get(Long id, String uid){
        Criteria criteria = criteria();
        criteria.add(Restrictions.and(Restrictions.eq("finish.fid", id), Restrictions.eq("seller.id", uid)));
        List<FinishSeller> finishSellers =  list(criteria);
        if(finishSellers.size()>0)return finishSellers.get(0);

        return null;
    }

    public boolean canDelete(FinishSeller finishSeller) {
        Criteria criteria = cardsFinishDAO.criteria();
        criteria.add(Restrictions.eq("fs_id", finishSeller.getId()));
        return criteria.list().size() == 0;
    }

}
