package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Item;
import com.neo.Model.RItem;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

@Service
@Transactional
public class ItemDAO extends AbstractDAO<Item, Long> {

    @Autowired
    public ItemDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Item.class);
    }

    public void updateItem(Item i, RItem r) {
        if (r == null) return;

        i.getTextBoxes().clear();
        for (RItem.RTextBox t : r.getTextBoxes())
            i.addTextBox(t.getContent(), t.getX(), t.getY(), t.getW(), t.getH());

        if (isNotEmpty(r.getQr())) {
            i.setQr(r.getQr());
            i.setQrs(r.getQrs());
            i.setQrx(r.getQrx());
            i.setQry(r.getQry());
        }

        if (isNotEmpty(r.getLogo())) {
            i.setLx(r.getLx());
            i.setLy(r.getLy());
            i.setLw(r.getLw());
            i.setLh(r.getLh());
        }

    }

    public boolean isValid(Item i) {
        return i != null && i.getImage()!=null;
    }
}
