package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.Order.DesignerOrder;
import com.neo.DatabaseModel.Order.Orders;
import com.neo.DatabaseModel.Users.Courier;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Designer;
import com.neo.DatabaseModel.Users.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.crypto.Des;

/**
 * Created by localadmin on 15/6/17.
 */

@Service
@Transactional
public class DesignerOrderDAO extends AbstractDAO<DesignerOrder, Long> {

    @Autowired
    public DesignerOrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory, DesignerOrder.class);
    }

    public boolean canCustomerCancel(DesignerOrder order, Customer customer) {
        if (!order.getCustomer().getId().equals(customer.getId())) return false;
        return canCustomerCancel(order);
    }
    public boolean canCustomerCancel(DesignerOrder order) {
        if (order.getStatus() == DesignerOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() > DesignerOrder.STATUS_RECEIVED_KEY) return false;
        return true;
    }

    public boolean canDesignerCancel(DesignerOrder order, Designer designer) {
        if (!order.getDesigner().getId().equals(designer.getId())) return false;
        return canDesignerCancel(order);
    }
    public boolean canDesignerCancel(DesignerOrder order) {
        if (order.getStatus() == DesignerOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() == DesignerOrder.STATUS_COMPLETED_KEY) return false;

        return true;
    }

    public boolean canChangeStatus(DesignerOrder order, Designer designer){
        if (!order.getDesigner().getId().equals(designer.getId())) return false;
        return canChangeStatus(order);
    }
    public boolean canChangeStatus(DesignerOrder order){
        if (order.getStatus() == DesignerOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() == DesignerOrder.STATUS_COMPLETED_KEY) return false;
        return true;
    }

    public boolean canComplain(DesignerOrder order){
        if (order.getStatus() == DesignerOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() == DesignerOrder.STATUS_COMPLETED_KEY) return false;

        return true;
    }

    public boolean canContact(Orders order) {
        return true;
    }

    public boolean canReview(DesignerOrder order){
        if (order.getStatus() != DesignerOrder.STATUS_COMPLETED_KEY) return false;
        return true;
    }
}
