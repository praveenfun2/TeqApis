package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Approval.CategoryApproval;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryApprovalDAO extends AbstractDAO<CategoryApproval, Long>{

    @Autowired
    public CategoryApprovalDAO(SessionFactory sessionFactory) {
        super(sessionFactory, CategoryApproval.class);
    }
}
