package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Shipment.DeliveryType;
import com.neo.Model.RDeliveryType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

@Service
@Transactional
public class DeliveryTypeDAO extends AbstractDAO<DeliveryType, Long> {

    @Autowired
    public DeliveryTypeDAO(SessionFactory sessionFactory) {
        super(sessionFactory, DeliveryType.class);
    }

    public void update(DeliveryType dt, RDeliveryType rdt){
        if(isNotEmpty(rdt.getTitle())) dt.setTitle(rdt.getTitle());
        if(isNotEmpty(rdt.getDescription())) dt.setDescription(rdt.getDescription());
        update(dt);
    }
}
