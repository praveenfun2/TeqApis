package com.neo.Service;

import com.neo.DatabaseModel.Users.Seller;
import com.neo.Model.RAddress;
import com.neo.Model.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

@Service
public class SellerService {
    private final AddressService addressService;

    @Autowired
    public SellerService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void update(RUser rUser, Seller seller) {
        if (isNotEmpty(rUser.getName())) seller.setName(rUser.getName());
        if (isNotEmpty(rUser.getPaypal())) seller.setPaypal(rUser.getPaypal());
        if (rUser.getLtime() != null) seller.setLtime(rUser.getLtime());
        if (rUser.getPrice() != null) seller.setPrice(rUser.getPrice());
        if (rUser.getDescription() != null) seller.setDescription(rUser.getDescription());
        if (isNotEmpty(rUser.getPhone())) seller.setPhone(rUser.getPhone());
    }

    public boolean isValid(Seller s) {
        return isNotEmpty(s.getName()) &&
                isNotEmpty(s.getPaypal()) &&
                isNotEmpty(s.getPhone()) &&
                s.getAddress() != null &&
                s.getLtime() != null && s.getLtime() > 0 &&
                s.getPrice() != null && s.getPrice() > 0;
    }

    public void buildRSeller(Seller seller, RUser rUser) {
        rUser.setName(seller.getName());
        rUser.setPaypal(seller.getPaypal());
        rUser.setDescription(seller.getDescription());
        rUser.setId(seller.getId());
        rUser.setLtime(seller.getLtime());
        rUser.setPrice(seller.getPrice());
        rUser.setPhone(seller.getPhone());

        RAddress rAddress = new RAddress();
        addressService.buildRAddress(seller.getAddress(), rAddress);
        rUser.setAddress(rAddress);
    }
}
