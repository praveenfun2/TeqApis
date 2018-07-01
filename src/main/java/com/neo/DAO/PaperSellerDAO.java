package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.PaperSeller;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by localadmin on 15/6/17.
 */

@Service
@Transactional
public class PaperSellerDAO extends AbstractDAO<PaperSeller, Long> {

    private final CardsPaperDAO cardsPaperDAO;

    @Autowired
    public PaperSellerDAO(SessionFactory sessionFactory, CardsPaperDAO cardsPaperDAO) {
        super(sessionFactory, PaperSeller.class);
        this.cardsPaperDAO = cardsPaperDAO;
    }

    public PaperSeller get(long pqid, String uid) {
        Criteria criteria = criteria();
        criteria.add(Restrictions.and(Restrictions.eq("paper.pqid", pqid), Restrictions.eq("seller.id", uid)));
        ArrayList<PaperSeller> paperSellers = (ArrayList<PaperSeller>) list(criteria);
        return paperSellers.size() > 0 ? paperSellers.get(0) : null;
    }

    public boolean canDelete(PaperSeller paperSeller) {
        Criteria criteria = cardsPaperDAO.criteria();
        criteria.add(Restrictions.eq("ps_id", paperSeller.getId()));
        return criteria.list().size() == 0;
    }
}
