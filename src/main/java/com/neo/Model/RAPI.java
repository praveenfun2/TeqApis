package com.neo.Model;

import com.neo.DatabaseModel.API;

/**
 * Created by localadmin on 5/7/17.
 */
public class RAPI {

    private API geocoding, email, paypal;

    public RAPI(API geocoding, API email, API paypal) {
        this.geocoding = geocoding;
        this.email = email;
        this.paypal = paypal;
    }

    public RAPI() {
    }

    public API getGeocoding() {
        return geocoding;
    }

    public void setGeocoding(API geocoding) {
        this.geocoding = geocoding;
    }

    public API getEmail() {
        return email;
    }

    public void setEmail(API email) {
        this.email = email;
    }

    public API getPaypal() {
        return paypal;
    }

    public void setPaypal(API paypal) {
        this.paypal = paypal;
    }
}
