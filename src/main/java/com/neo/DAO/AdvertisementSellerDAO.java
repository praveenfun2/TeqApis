package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Advertisement;
import com.neo.DatabaseModel.AdvertisementSeller;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by localadmin on 17/6/17.
 */

@Service
@Transactional
public class AdvertisementSellerDAO extends AbstractDAO<AdvertisementSeller, Long> {

    private static final int MAX_AD=5;

    @Autowired
    public AdvertisementSellerDAO(SessionFactory sessionFactory) {
        super(sessionFactory, AdvertisementSeller.class);
    }

    /*@Scheduled(cron = "0 23 * * *")
    public void deleteCron(){
        currentSession().createSQLQuery("delete from advertisement_seller where edate< CURDATE()")
                .executeUpdate();
    }
*/
    public List<AdvertisementSeller> getAdvertisements(String city, String state, String country){
        List<AdvertisementSeller> advertisementSellers=new ArrayList<>();

        Criteria criteria=criteria();
        LogicalExpression one=Restrictions.and(Restrictions.eq("areaName", city),
                Restrictions.eq("type", Advertisement.CITY_TYPE));
        LogicalExpression two=Restrictions.and(Restrictions.eq("areaName", state),
                Restrictions.eq("type", Advertisement.STATE_TYPE));
        LogicalExpression three=Restrictions.and(Restrictions.eq("areaName", country),
                Restrictions.eq("type", Advertisement.COUNTRY_TYPE));

        criteria.add(Restrictions.or(one, two, three));
        long time =new Date().getTime();
        criteria.add(Restrictions.lt("sTimestamp", time));
        criteria.add(Restrictions.gt("eTimestamp", time));

        Number count= (Number) criteria().setProjection(Projections.rowCount()).uniqueResult();
        if(count.intValue()<MAX_AD) return criteria.list();
        HashSet<Integer> randoms=new HashSet<>(MAX_AD);
        int i=0;
        while(i<MAX_AD){
            int num= (int) (Math.random()*count.intValue());
            if(!randoms.contains(num)){
                randoms.add(num);
                i++;
            }
        }
        for(int num: randoms){
            criteria.setMaxResults(1).setFirstResult(num);
            AdvertisementSeller advertisementSeller=getOneByCriteria(criteria);
            advertisementSellers.add(advertisementSeller);
        }

        return advertisementSellers;
    }

}
