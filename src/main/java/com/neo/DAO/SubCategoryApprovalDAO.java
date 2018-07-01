package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Approval.SubCategoryApproval;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubCategoryApprovalDAO extends AbstractDAO<SubCategoryApproval, Long>{

    @Autowired
    public SubCategoryApprovalDAO(SessionFactory sessionFactory) {
        super(sessionFactory, SubCategoryApproval.class);
    }
}
