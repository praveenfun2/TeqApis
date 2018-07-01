package com.neo;

import com.neo.DAO.APIDAO;
import com.neo.Utils.GeneralHelper;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

import static com.neo.Constants.HTTPS;
import static com.neo.Constants.WEBSITE_DOMAIN;
import static com.neo.Utils.GeneralHelper.encodeString;

/**
 * Created by localadmin on 11/7/17.
 */

@Component
public class EmailHelper {

    private String companyDomain = "teqni@Teqnicard.com";
    private final APIDAO apidao;
    private SendGrid sendGrid;
    private boolean apiChanged = true;

    @Autowired
    public EmailHelper(APIDAO apidao) {
        this.apidao = apidao;
    }

    private SendGrid getSendGrid() {
        if (sendGrid == null || apiChanged)
            sendGrid = new SendGrid(apidao.emailApi().getId());
        apiChanged = false;
        return sendGrid;
    }

    public boolean OTPMail(int otp, String receiver, String type) {
        HashMap<String, String> params=new HashMap<>();
        params.put("id", receiver);
        params.put("otp", otp+"");
        params.put("type", type);

        String url=GeneralHelper.url(HTTPS, WEBSITE_DOMAIN, "/html/confirmation.html", params);
        return send(companyDomain, "Email Verification", receiver, url);
    }

    public void complaint(String receiver) {
        String content = "There is a reply to your complaint. Login to your dashboard to see.";
        send(companyDomain, "Complaint", receiver, content);
    }

    public void newTicket(String receiver, Long ticketId) {
        String content = "Your ticket with id " + ticketId + " has been registered";
        send(companyDomain, "Complaint", receiver, content);
    }

    public boolean changePassLink(String to, String token) {
        HashMap<String, String > params=new HashMap<>();
        params.put("token", token);
        params.put("id", to);
        String url= GeneralHelper.url("https", WEBSITE_DOMAIN, "/html/recovery.html", params);
        return send(companyDomain, "Change Password", to, url);
    }

    private boolean send(String from, String subject, String to, String content) {
        Mail mail = new Mail(new Email(from, "Teqnicard"), subject, new Email(to), new Content("text/plain", content));

        SendGrid sg = getSendGrid();
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sg.api(request);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setApiChanged() {
        this.apiChanged = true;
    }
}
