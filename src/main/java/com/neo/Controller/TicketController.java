package com.neo.Controller;

import com.neo.Constants;
import com.neo.DAO.*;
import com.neo.DatabaseModel.Message.Complaint;
import com.neo.DatabaseModel.Order.Orders;
import com.neo.DatabaseModel.Ticket;
import com.neo.DatabaseModel.Users.User;
import com.neo.EmailHelper;
import com.neo.Model.RComplaintReason;
import com.neo.Model.RComplaints;
import com.neo.Model.RMessage;
import com.neo.Utils.DateTime;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.neo.DatabaseModel.Ticket.*;

/**
 * Created by localadmin on 11/7/17.
 */

@RestController
@CrossOrigin(Constants.FRONT_END)
@RequestMapping("/complaint")
public class TicketController {

    private final UserDAO userDAO;
    private final TicketDAO ticketDAO;
    private final ComplaintDAO complaintDAO;
    private final DesignerOrderDAO designerOrderDAO;
    private final CardOrderDAO cardOrderDAO;
    private final DateTime dateTime;
    private final OrderDAO orderDAO;
    private final EmailHelper emailHelper;

    @Autowired
    public TicketController(UserDAO userDAO, TicketDAO ticketDAO, ComplaintDAO complaintDAO, DesignerOrderDAO designerOrderDAO,
                            CardOrderDAO cardOrderDAO, DateTime dateTime, OrderDAO orderDAO, EmailHelper emailHelper) {
        this.userDAO = userDAO;
        this.ticketDAO = ticketDAO;
        this.complaintDAO = complaintDAO;
        this.designerOrderDAO = designerOrderDAO;
        this.cardOrderDAO = cardOrderDAO;
        this.dateTime = dateTime;
        this.orderDAO = orderDAO;
        this.emailHelper = emailHelper;

    }

    private static Map<Integer, String> complaintReasonsMap;

    static {
        complaintReasonsMap = new HashMap<>();
        complaintReasonsMap.put(REASON_1_KEY, REASON_1_VALUE);
        complaintReasonsMap.put(REASON_2_KEY, REASON_2_VALUE);
        complaintReasonsMap.put(REASON_3_KEY, REASON_3_VALUE);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean complaint(@RequestHeader("Authorization") String token, @RequestBody RMessage rComplaint) {
        User user = userDAO.get(JWTHelper.decodeToken(token).getId());
        if (user == null) return false;

        Ticket ticket = ticketDAO.get(rComplaint.getTicketId());
        Orders order;
        if (ticket == null) {
            order = orderDAO.get(rComplaint.getOrderId());
            if (order == null) return false;

            ticket = ticketDAO.getByOrder(rComplaint.getOrderId());
            if (ticket == null) {
                int r = rComplaint.getReason();
                if (!complaintReasonsMap.containsKey(r)) return false;
                ticket = new Ticket(r, order, user);
                ticketDAO.save(ticket);
            }
        } else order = ticket.getOrder();

        Complaint complaint = new Complaint(rComplaint.getMessage(), user, ticket);
        complaintDAO.save(complaint);

        User consumer = order.getConsumer(), provider = order.getProvider();
        if (consumer.getId().equals(user.getId()))
            emailHelper.complaint(provider.getId());
        else if (provider.getId().equals(user.getId()))
            emailHelper.complaint(consumer.getId());

        return true;
    }

    @RequestMapping(value = {"/reasons/seller", "/reasons/customer"})
    public List<RComplaintReason> complaintReasons() {
        List<RComplaintReason> rComplaintReasons = new ArrayList<>();
        Set<Map.Entry<Integer, String>> set = complaintReasonsMap.entrySet();
        for (Map.Entry<Integer, String> e : set)
            rComplaintReasons.add(new RComplaintReason(e.getKey(), e.getValue()));
        return rComplaintReasons;
    }

    @RequestMapping("/complaints")
    public RComplaints getChats(@RequestHeader("Authorization") String token, @RequestParam(required = false) Long orderId,
                                @RequestParam(required = false) Long ticketId) {
        RComplaints rComplaints = null;

        User user = userDAO.get(JWTHelper.decodeToken(token).getId());
        if (user == null) return rComplaints;

        Ticket ticket;
        Orders order = null;

        if ((ticket = ticketDAO.get(ticketId)) != null)
            order = ticket.getOrder();
        else if ((order = orderDAO.get(orderId)) != null) {
            ticket = ticketDAO.getByOrder(orderId);
            if (ticket == null) return rComplaints;
        } else return rComplaints;

        List<Complaint> complaints = ticket.getComplaints();
        boolean canResolve = ticket.getIssuer().getId().equals(user.getId());
        String reason = complaintReasonsMap.get(ticket.getReason());
        if (reason == null) return rComplaints;

        rComplaints = new RComplaints(order.getId(), ticket.getId(), canResolve, reason);
        for (Complaint complaint : complaints) {
            User sender = complaint.getSender();
            String time = dateTime.getFormatedTime(complaint.getTimestamp());
            rComplaints.addMessage(complaint.getMessage(), user.getId().equals(sender.getId()) ? "You:" : user.getName() + ":", time);
        }
        return rComplaints;
    }

    @RequestMapping("/resolveTicket")
    public boolean resolve(@RequestHeader("Authorization") String token, @RequestParam(required = false) Long orderId,
                           @RequestParam(required = false) Long ticketId) {
        User user = userDAO.get(JWTHelper.decodeToken(token).getId());
        if (user == null) return false;

        Ticket ticket;

        if ((ticket = ticketDAO.get(ticketId)) == null && (ticket = ticketDAO.getByOrder(orderId)) == null)
            return false;

        if (ticket.getIssuer().getId().equals(user.getId())) {
            ticket.setResolved(true);
            ticketDAO.update(ticket);
            return true;
        }
        return false;
    }

}
