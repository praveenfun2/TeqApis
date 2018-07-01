package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Approval.Approval;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 16/6/17.
 */

@Service
@Transactional
public class ApprovalDAO extends AbstractDAO<Approval, Long> {

    @Autowired
    public ApprovalDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Approval.class);
    }
}
