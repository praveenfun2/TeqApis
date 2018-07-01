package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Order.CardOrder;
import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.DatabaseModel.Order.Orders;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Users.Seller;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by localadmin on 17/6/17.
 */

@Service
@Transactional
public class CardOrderDAO extends AbstractDAO<CardOrder, Long> {

    @Autowired
    public CardOrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory, CardOrder.class);
    }

    public boolean canCustomerCancel(CardOrder order, Customer customer) {
        if (!order.getCustomer().getId().equals(customer.getId())) return false;
        return canCustomerCancel(order);
    }

    public boolean canCustomerCancel(CardOrder order) {
        if (order.getStatus() == CardOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() > CardOrder.STATUS_RECEIVED_KEY) return false;

        return true;
    }

    public boolean canSellerCancel(CardOrder order, Seller seller) {
        if (!order.getSeller().getId().equals(seller.getId())) return false;
        return canSellerCancel(order);
    }

    public boolean canSellerCancel(CardOrder order) {
        if (order.getStatus() == CardOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() > CardOrder.STATUS_PRINTED_KEY) return false;
        return true;
    }

    public boolean canChangeStatus(CardOrder order, Seller seller) {
        return order != null && seller != null && canChangeStatus(order) && order.getSeller().getId().equals(seller.getId()) &&
                order.getCourierOrder() == null;
    }

    public boolean canChangeStatus(CardOrder order) {
        int st = order.getStatus();
        return st >= CardOrder.STATUS_RECEIVED_KEY && st <= CardOrder.STATUS_PRINTED_KEY;
    }

    public boolean canComplain(CardOrder order) {
        if (order.getStatus() == CardOrder.STATUS_CANCELED_KEY) return false;
        if (order.getStatus() == CourierOrder.STATUS_DELIVERED_KEY) return false;

        return true;
    }

    public boolean canContact(Orders order) {
        return true;
    }

    public boolean canReview(CourierOrder order) {
        if (order == null) return false;
        if (order.getStatus() != CourierOrder.STATUS_DELIVERED_KEY) return false;
        return true;
    }
}
