package com.neo.Controller;

import com.neo.DatabaseModel.Address;
import com.neo.Model.RAddress;
import com.neo.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * @api {post} /address/get Get Address
     * @apiDescription Get complete address using postal code & addressline.
     * @apiGroup Address
     * @apiParam {String} postalCode Pincode/Zipcode.
     * @apiParam {String} addressLine Address Line to identify a location within a city.
     * @apiSuccessExample {json} Response:-
     * {
     * "postalCode": Pincode/Zipcode,
     * "addressLine": Address Line,
     * "city": City,
     * "state": State,
     * "country": Country
     * }
     * @apiError (400) - Invalid pincode/addressLine combination.
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public RAddress check(@RequestBody RAddress rAddress, HttpServletResponse response) {
        Address address = new Address();
        addressService.update(rAddress, address);

        if (addressService.isValid(address)) {
            addressService.buildRAddress(address, rAddress);
            return rAddress;
        } else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return null;
    }
}
