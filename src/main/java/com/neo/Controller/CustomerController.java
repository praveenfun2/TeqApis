/*
package com.neo.Controller;

import com.neo.Constants;
import com.neo.DAO.*;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.Users.Customer;
import com.neo.DatabaseModel.Card.CustomerCard;
import com.neo.Model.RAddress;
import com.neo.Model.RUser;
import com.neo.Service.AddressService;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(Constants.FRONT_END)
public class CustomerController {

    private final AddressDAO addressDAO;
    private final AddressService addressService;
    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerController(AddressDAO addressDAO, AddressService addressService, CustomerDAO customerDAO) {
        this.addressDAO = addressDAO;
        this.addressService = addressService;
        this.customerDAO = customerDAO;
    }

    @RequestMapping("/getCustomer")
    public RUser getCustomer(@RequestHeader("Authorization") String token) throws SQLException {
        Customer customer= customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer==null) return null;
        return new RUser(customer.getId(), customer.getName());
    }

    @RequestMapping("/getAddresses")
    public List<RAddress> getAddresses(@RequestHeader("Authorization") String token) throws SQLException {
        Customer customer= customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer==null) return null;

        List<RAddress> addresses=new ArrayList<>();
        for(Address address: customer.getAddresses()) {
            RAddress rAddress=new RAddress();
            rAddress.build(address);
            rAddress.setAddressString(addressService.addressStringWithoutPhoneAndName(address));
            addresses.add(rAddress);
            address.setCustomer(null);
        }
        return addresses;
    }

    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    public Long addAddresses(@RequestBody RAddress rAddress, @RequestHeader("Authorization") String token) throws SQLException {
        Customer customer= customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer==null) return null;

        Address address=addressService.getAddress(rAddress, null);
        if(address==null) return null;

        address.setCustomer(customer);
        addressDAO.update(address);

        return address.getId();
    }

    @RequestMapping("/deleteAddress")
    public boolean deleteAddress(@RequestHeader("Authorization") String token, Long id) throws SQLException {
        Customer customer= customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer==null) return false;

        Address address=addressDAO.get(id);
        if(address==null || !address.getCustomer().getId().equals(customer.getId())) return false;

        addressDAO.delete(address);
        return true;
    }

    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    public boolean updateAddress(@RequestHeader("Authorization") String token, @RequestBody RAddress address){
        Customer customer= customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer==null) return false;

        Address address1=addressDAO.get(address.getId());
        if(address1==null || !address1.getCustomer().getId().equals(customer.getId())) return false;

        address1 = addressService.getAddress(address, address1);
        if(address1==null) return false;

        addressDAO.update(address1);

        return true;
    }

    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    public boolean updateSeller(@RequestBody RUser rUser, @RequestHeader("Authorization") String token) throws SQLException {

        Customer customer= customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer==null) return false;

        customer.setName(rUser.getName());
        customerDAO.update(customer);

        return true;
    }

    @RequestMapping("/getUploadedDesigns")
    public List<CustomerCard> getUploadedDesigns(@RequestHeader("Authorization") String token) throws SQLException {

        Customer customer= customerDAO.get(JWTHelper.decodeToken(token).getId());
        if (customer==null) return new ArrayList<>();

        List<CustomerCard> userCards=customer.getCards();
        for(CustomerCard userCard: userCards) {
            userCard.setCustomer(null);
            userCard.setSeller(null);
        }

        return userCards;
    }

}
*/
