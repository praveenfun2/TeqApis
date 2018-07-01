package com.neo.DAO;

import com.neo.AbstractDAO;
import com.neo.DatabaseModel.Rating;
import com.neo.DatabaseModel.Users.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingDAO extends AbstractDAO<Rating, Long> {

    @Autowired
    public RatingDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Rating.class);
    }

    public Rating rateUser(int stars, String review, User user, Rating rating){
        if(review.length()>1024) return null;
        if(stars<1 || stars>5) return null;

        if(rating==null) {
            rating=new Rating(stars, review, user);
            long rating1=user.getRating()+stars;
            int ratingCount=user.getRatingCount()+1;
            user.setRating(rating1);
            user.setRatingCount(ratingCount);
        }else {
            long rating1=user.getRating()+stars-rating.getRating();
            rating.setRating(stars);
            rating.setReview(review);
            user.setRating(rating1);
        }

        return rating;
    }
}
