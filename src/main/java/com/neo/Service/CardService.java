package com.neo.Service;

import com.neo.DAO.CategoryDAO;
import com.neo.DAO.FinishSellerDAO;
import com.neo.DAO.PaperSellerDAO;
import com.neo.DAO.SubCategoryDAO;
import com.neo.DatabaseModel.Card.Card;
import com.neo.DatabaseModel.Card.SellerCard;
import com.neo.DatabaseModel.Card.SellerCardBack;
import com.neo.DatabaseModel.Card.SellerCardFront;
import com.neo.DatabaseModel.*;
import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.neo.Utils.GeneralHelper.isNotEmpty;
import static com.neo.Utils.GeneralHelper.sizeOf;

@Service
public class CardService {
    private final CategoryDAO categoryDAO;
    private final SubCategoryDAO subCategoryDAO;
    private final FinishSellerDAO finishSellerDAO;
    private final PaperSellerDAO paperSellerDAO;

    @Autowired
    public CardService(CategoryDAO categoryDAO, SubCategoryDAO subCategoryDAO, FinishSellerDAO finishSellerDAO, PaperSellerDAO paperSellerDAO) {
        this.categoryDAO = categoryDAO;
        this.subCategoryDAO = subCategoryDAO;
        this.finishSellerDAO = finishSellerDAO;
        this.paperSellerDAO = paperSellerDAO;
    }

    private void update(RCard rCard, SellerCard card) {
        String name = rCard.getName();
        if (isNotEmpty(name)) card.setName(name);
        Float price = rCard.getPrice();
        if (price != null && price > 0) card.setPrice(price);
        rCard.setLandscape(card.isLandscape());
    }

    public void update(RCard rCard, SellerCardFront card) {
        update(rCard, (SellerCard) card);
        Seller seller = card.getSeller();

        Category category = categoryDAO.get(rCard.getCat());
        if (category != null && category.getCatid() != card.getCategory().getCatid()) {
            card.setCategory(category);
            card.getSubcats().clear();
        }

        category = card.getCategory();
        if (rCard.getSubcat() != null) {
            card.getSubcats().clear();
            for (long aSa : rCard.getSubcat()) {
                SubCategory subCategory = subCategoryDAO.get(aSa);
                if (subCategory != null && subCategory.getCategory().getCatid() == category.getCatid())
                    card.getSubcats().add(new CardsSubcat(card, subCategory));
            }
        }


        if (rCard.getPaper() != null) {
            card.getPapers().clear();
            for (long aSa : rCard.getPaper()) {
                PaperSeller ps = paperSellerDAO.get(aSa, seller.getId());
                if (ps != null) card.getPapers().add(new CardsPaper(card, ps));
            }
        }

        if (rCard.getFinish() != null) {
            card.getFinishes().clear();
            for (long aSa : rCard.getFinish()) {
                FinishSeller fs = finishSellerDAO.get(aSa, seller.getId());
                if (fs != null) card.getFinishes().add(new CardsFinish(card, fs));
            }
        }


    }

    public void update(RCard rCard, SellerCardBack card) {
        update(rCard, (SellerCard) card);
    }

    public boolean isValid(SellerCardFront card) {
        return isValid((SellerCard) card)
                && card.getCategory() != null
                && sizeOf(card.getSubcats()) > 0
                && sizeOf(card.getPapers()) > 0
                && sizeOf(card.getFinishes()) > 0;
    }

    public boolean isValid(SellerCardBack card) {
        return isValid(((SellerCard) card));
    }

    private boolean isValid(SellerCard card) {
        return isNotEmpty(card.getName())
                && card.getPrice() > 0;
    }
}

