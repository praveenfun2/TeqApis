/*
package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.Order.Orders;
import com.neo.DatabaseModel.Users.Courier;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.DatabaseModel.Users.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.DatabaseModel.Order.CourierOrder.STATUS_CANCELED_KEY;
import static com.neo.DatabaseModel.Order.CourierOrder.STATUS_DELIVERED_KEY;
import static com.neo.DatabaseModel.Order.CourierOrder.STATUS_RECEIVED_KEY;
import static com.neo.Utils.GeneralHelper.isNotEmpty;

@Service
@Transactional
public class CourierOrderDAO extends AbstractDAO<CourierOrder, Long> {

    @Autowired
    public CourierOrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory, CourierOrder.class);
    }

    public boolean canSellerCancel(CourierOrder order, Seller seller) {
        if(!seller.isValidated()) return false;
        if (!order.getSeller().getId().equals(seller.getId())) return false;
        return canSellerCancel(order);
    }
    public boolean canSellerCancel(CourierOrder order) {
        if (order.getStatus() == CourierOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() > CourierOrder.STATUS_RECEIVED_KEY) return false;
        return true;
    }

    public boolean canCourierCancel(CourierOrder order, Courier courier) {
        if (!order.getCourier().getId().equals(courier.getId())) return false;
        return canCourierCancel(order);
    }
    public boolean canCourierCancel(CourierOrder order) {
        if (order.getStatus() == CourierOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() == CourierOrder.STATUS_DELIVERED_KEY) return false;

        return true;
    }

    public boolean canChangeStatus(CourierOrder order, Courier courier){
        if (!order.getCourier().getId().equals(courier.getId())) return false;
        return canChangeStatus(order);
    }
    public boolean canChangeStatus(CourierOrder order){
        if (order.getStatus() == CourierOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() == CourierOrder.STATUS_DELIVERED_KEY) return false;
        if(order.getStatus()<STATUS_RECEIVED_KEY) return false;
        if(order.getStatus()>STATUS_DELIVERED_KEY) return false;
        return true;
    }

    public boolean canComplain(CourierOrder order){
        if (order.getStatus() == CourierOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() == CourierOrder.STATUS_DELIVERED_KEY) return false;

        return true;
    }

    public boolean canContact(Orders order) {
        return true;
    }

    public boolean canReview(CourierOrder order){
        if (order.getStatus() != CourierOrder.STATUS_DELIVERED_KEY) return false;
        return true;
    }
}
*/
