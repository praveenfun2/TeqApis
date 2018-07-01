package com.neo;

import com.neo.DAO.APIDAO;
import com.neo.DatabaseModel.API;
import com.neo.Utils.GeneralHelper;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Praveen Gupta on 6/3/2017.
 */
@Component
public class PayPalHelper {

    private APIDAO apidao;
    private boolean apiChanged = true;
    private String secret = "";
    private String clientID = "";
    private APIContext apiContext;

    @Autowired
    public PayPalHelper(APIDAO apidao) {
        this.apidao = apidao;
    }

    private APIContext getApiContext() {
        if (apiChanged) {
            API api = apidao.paypalApi();
            secret = api.getSecret();
            clientID = api.getId();
        }
        apiChanged = false;
        apiContext = new APIContext(clientID, secret, "sandbox");
        return apiContext;
    }

    public String executePayment(String payerID, String paymentID, float amt) {
        try {
            Payment payment = Payment.get(getApiContext(), paymentID);
            for (Transaction t : payment.getTransactions())
                amt -= Float.parseFloat(t.getAmount().getTotal());
            if (amt != 0) return null;

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerID);

            Payment payment1 = payment.execute(getApiContext(), paymentExecution);
            return payment1.getTransactions().get(0).getRelatedResources().get(0).getSale().getId();
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean refundPayment(String saleId, float amt) {
        RefundRequest refundRequest = new RefundRequest();

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", amt));

        refundRequest.setAmount(amount);

        try {
            Sale sale = new Sale();
            sale.setId(saleId);
            DetailedRefund detailedRefund = sale.refund(getApiContext(), refundRequest);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean executePayment(float amt, String payTo) {

        Payout payout = new Payout();
        PayoutItem payoutItem = new PayoutItem();

        payoutItem.setAmount(new Currency().setCurrency("USD").setValue(String.valueOf(GeneralHelper.round2(amt))));
        payoutItem.setRecipientType("EMAIL");
        payoutItem.setReceiver(payTo);

        ArrayList<PayoutItem> payoutItems = new ArrayList<>();
        payoutItems.add(payoutItem);

        payout.setItems(payoutItems);

        try {
            PayoutBatch payoutBatch = payout.createSynchronous(getApiContext());
            com.paypal.api.payments.Error error = payoutBatch.getBatchHeader().getErrors();
            return true;
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void setApiChanged() {
        this.apiChanged = true;
    }
}
