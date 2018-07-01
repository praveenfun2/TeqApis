package com.neo.Controller.Designer;

import com.neo.DAO.DesignerBidDAO;
import com.neo.DAO.DesignerDAO;
import com.neo.DAO.DesignerOfferDAO;
import com.neo.DAO.NotificationDAO;
import com.neo.DatabaseModel.DesignerBid;
import com.neo.DatabaseModel.DesignerOffer;
import com.neo.DatabaseModel.Notification;
import com.neo.DatabaseModel.Users.Designer;
import com.neo.Model.RDesignerBid;
import com.neo.Model.RDesignerOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.neo.Utils.GeneralHelper.round2;

@RestController
@RequestMapping("/designer/bid")
public class BidController {

    private final DesignerDAO designerDAO;
    private final DesignerBidDAO designerBidDAO;
    private final NotificationDAO notificationDAO;
    private final DesignerOfferDAO designerOfferDAO;

    @Autowired
    public BidController(DesignerDAO designerDAO, DesignerBidDAO designerBidDAO, NotificationDAO notificationDAO, DesignerOfferDAO designerOfferDAO) {
        this.designerDAO = designerDAO;
        this.designerBidDAO = designerBidDAO;
        this.notificationDAO = notificationDAO;
        this.designerOfferDAO = designerOfferDAO;
    }

    @RequestMapping("")
    public List<RDesignerBid> getAll(@RequestAttribute String id) throws SQLException {
        Designer designer = designerDAO.get(id);

        List<RDesignerBid> rDesignerBids = new ArrayList<>();

        List<DesignerBid> bids = designer.getBids();
        for (DesignerBid bid : bids) {
            DesignerOffer offer = bid.getOffer();
            RDesignerOffer rDesignerOffer = new RDesignerOffer(offer.getId(), offer.getDescription(),
                    offer.getCategory().getName(), offer.getSubCategory().getName());
            RDesignerBid rDesignerBid = new RDesignerBid(bid.getId(), rDesignerOffer, bid.getAmt(),
                    Integer.parseInt(bid.getDuration()), offer.getCustomer().getId());
            rDesignerBids.add(rDesignerBid);
        }
        return rDesignerBids;
    }

    @RequestMapping("/delete")
    public void delete(@RequestAttribute String id, @RequestParam Long bid, HttpServletResponse response) {
        Designer designer = designerDAO.get(id);

        DesignerBid b = designerBidDAO.get(bid);
        if (b == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else if (!b.getDesigner().getId().equals(designer.getId()))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
            designerBidDAO.delete(bid);
    }

    @RequestMapping("/bid")
    public Long bid(@RequestParam float amt, String etime, long ofid, @RequestAttribute String id, HttpServletResponse response) throws SQLException {
        Designer designer = designerDAO.get(id);

        DesignerOffer offer = designerOfferDAO.get(ofid);
        if (offer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        DesignerBid designerBid = new DesignerBid(offer, round2(amt), etime, designer);
        designerBidDAO.save(designerBid);

        notificationDAO.addNotification(Notification.TYPE_DESIGNER_ORDER,
                Notification.MESSAGE_NEW_BID, offer.getCustomer());

        return designerBid.getId();
    }
}