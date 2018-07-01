package com.neo.Service;

import com.neo.DAO.CourierDAO;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Users.Courier;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourierService {
    private final CourierDAO courierDAO;

    @Autowired
    public CourierService(CourierDAO courierDAO) {
        this.courierDAO = courierDAO;
    }

    public List<Courier> getFilteredCouriers(float minprice, float maxprice, String order, Address sellerAddress, Address deliveryAddress) {
        Criteria criteria = courierDAO.criteria();
        criteria.createAlias("address", "ad");
        criteria.createAlias("coverages", "cov");

        if (deliveryAddress.getCity().equals(sellerAddress.getCity())) {
            criteria.add(Restrictions.eq("ad.city", deliveryAddress.getCity()));
            criteria.add(Restrictions.eq("cov.coverage", Courier.CITY_COVERAGE_KEY));
        } else if (deliveryAddress.getState().equals(sellerAddress.getState())) {
            criteria.add(Restrictions.eq("ad.state", deliveryAddress.getState()));
            criteria.add(Restrictions.eq("cov.coverage", Courier.STATE_COVERAGE_KEY));
        } else if (deliveryAddress.getCountry().equals(sellerAddress.getCountry())) {
            criteria.add(Restrictions.eq("ad.country", deliveryAddress.getCountry()));
            criteria.add(Restrictions.eq("cov.coverage", Courier.COUNTRY_COVERAGE_KEY));
        } else
            criteria.add(Restrictions.eq("cov.coverage", Courier.INTERNATIONAL_COVERAGE_KEY));

        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }

}
