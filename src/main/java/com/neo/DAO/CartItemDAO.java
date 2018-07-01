package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Card.Card;
import com.neo.DatabaseModel.Card.CustomerCard;
import com.neo.DatabaseModel.CartItem;
import com.neo.DatabaseModel.Item;
import com.neo.DatabaseModel.Users.Customer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CartItemDAO extends AbstractDAO<CartItem, Long> {

    @Autowired
    public CartItemDAO(SessionFactory sessionFactory, SellerCardDAO sellerCardDAO) {
        super(sessionFactory, CartItem.class);
        this.sellerCardDAO = sellerCardDAO;
    }
    private final SellerCardDAO sellerCardDAO;

    public void deleteCartItems(List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) delete(cartItem);
    }

    public float getTotalCartPrice(Customer customer) {
        List<CartItem> cartItems=customer.getCartItems();
        float sum = 0;
        for (CartItem cartItem : cartItems) sum += getCartItemPrice(cartItem);

        return sum;
    }

    public float getCartItemPrice(CartItem cartItem) {
        float sum = cartItem.getPaperSeller().getPrice() + cartItem.getFinishSeller().getPrice();

        Item fItem = cartItem.getfItem();
        Item bItem = cartItem.getbItem();

        Card card = fItem.getImage().getCard();
        if (card.getType() == Card.CUSTOMER_CARD)
            sum += card.getSeller().getPrice();
        else sum += sellerCardDAO.getPrice(card.getId());

        if (bItem != null) sum += sellerCardDAO.getPrice(bItem.getImage().getCard().getId());

        sum = ((int) (sum * cartItem.getQuantity())) / 100f;

        return sum;
    }

    public boolean isCardDeletable(CustomerCard userCard) {
        Criteria criteria = criteria();
        criteria.add(Restrictions.eq("customer.id", userCard.getCustomer().getId()))
                .createAlias("fItem", "i");
        criteria.add(Restrictions.eq("i.card.id", userCard.getId()));
        criteria.setProjection(Projections.rowCount());

        return (long) criteria.uniqueResult() == 0;
    }
/*
    public CartItem getByItemId(Long id) {
        if (id == null) return null;

        Criteria criteria = criteria();
        criteria.add(Restrictions.eq("fItem.id", id));
        return getOneByCriteria(criteria);
    }*/
}
