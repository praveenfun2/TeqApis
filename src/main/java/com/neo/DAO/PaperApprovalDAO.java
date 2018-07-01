package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Approval.PaperApproval;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaperApprovalDAO extends AbstractDAO<PaperApproval, Long>{

    @Autowired
    public PaperApprovalDAO(SessionFactory sessionFactory) {
        super(sessionFactory, PaperApproval.class);
    }
}
