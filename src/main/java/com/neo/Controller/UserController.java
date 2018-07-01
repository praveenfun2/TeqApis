package com.neo.Controller;

import com.neo.DAO.*;
import com.neo.DatabaseModel.Address;
import com.neo.DatabaseModel.PassToken;
import com.neo.DatabaseModel.Users.*;
import com.neo.EmailHelper;
import com.neo.Model.RAddress;
import com.neo.Model.RAuth;
import com.neo.Model.RPasswordToken;
import com.neo.Model.RUser;
import com.neo.Service.AddressService;
import com.neo.Service.SellerService;
import com.neo.Utils.GeneralHelper;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import static com.neo.DatabaseModel.Users.User.SELLER_TYPE;
import static com.neo.Utils.GeneralHelper.OTPGenerator;
import static com.neo.Utils.GeneralHelper.encodeString;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDAO userDAO;
    private final CourierDAO courierDAO;
    private final SellerDAO sellerDAO;
    private final DesignerDAO designerDAO;
    private final CustomerDAO customerDAO;
    private final EmailHelper emailHelper;
    private final PassTokenDAO passTokenDAO;
    private final AddressService addressService;
    private final SellerService sellerService;

    @Autowired
    public UserController(UserDAO userDAO, CourierDAO courierDAO, SellerDAO sellerDAO, DesignerDAO designerDAO, CustomerDAO customerDAO,
                          EmailHelper emailHelper, PassTokenDAO passTokenDAO, AddressService addressService, SellerService sellerService) {
        this.userDAO = userDAO;
        this.courierDAO = courierDAO;
        this.sellerDAO = sellerDAO;
        this.designerDAO = designerDAO;
        this.customerDAO = customerDAO;
        this.emailHelper = emailHelper;
        this.passTokenDAO = passTokenDAO;
        this.addressService = addressService;
        this.sellerService = sellerService;
    }

    /**
     * @api {post} /user/auth User Login
     * @apiGroup Auth
     * @apiSuccess {String} token token required for every request
     * @apiSuccess {String="seller", "courier", "customer", "designer"} type user type
     * @apiParam {String} id Email Address
     * @apiParam {String} password Password
     * @apiError (401) - Username/Password is wrong
     * @apiError (403) - Email is not verified
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public RAuth UserAuth(@RequestBody RUser rUser, HttpServletResponse response) throws UnknownHostException, SQLException, UnsupportedEncodingException {
        User user = userDAO.get(rUser.getId());
        if (user == null || !GeneralHelper.encodeString(rUser.getPassword()).equals(user.getPass()))
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        else if (!user.isActivated())
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
            return new RAuth(JWTHelper.createToken(rUser.getId(), rUser.getPassword()), user.getType());

        return null;
    }

    /**
     * @api {post} /user/new Register
     * @apiDescription New User Registration
     * @apiGroup Auth
     * @apiParam {String} id Email Address
     * @apiParam {String} password password
     * @apiParam {String="customer", "seller", "courier", "designer"} type User types.
     * @apiParam {String} name Full Name of user
     * @apiError (400) - Wrong user type provided
     * @apiError (403) - User with given email already exists
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void NewUser(@RequestBody RUser rUser, HttpServletResponse response) throws SQLException {

        String uid = rUser.getId(), type = rUser.getType(), pass = rUser.getPassword(), name = rUser.getName();

        User user = userDAO.get(uid);
        if (user != null) response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else {
            int otp = OTPGenerator();
            switch (type) {
                case User.COURIER_TYPE:
                    Courier courier = new Courier(uid, encodeString(pass), name, otp);
                    courierDAO.save(courier);
                    break;

                case User.SELLER_TYPE:
                    Seller seller = new Seller(uid, encodeString(pass), name, otp);
                    sellerDAO.save(seller);
                    break;

                case User.DESIGNER_TYPE:
                    Designer designer = new Designer(uid, encodeString(pass), name, otp);
                    designerDAO.save(designer);
                    break;

                case User.CUSTOMER_TYPE:
                    Customer customer = new Customer(uid, encodeString(pass), name, otp);
                    customerDAO.save(customer);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
            }
            emailHelper.OTPMail(otp, uid, type);
        }
    }

    /**
     * @api {post} /user/OTP Email Verification
     * @apiDescription Verify email address after registration
     * @apiGroup Auth
     * @apiParamExample {json} Request:-
     * {
     * "id":Email address of the user,
     * "otp": "OTP sent to the email address after registration",
     * //for seller account
     * "address":{"postalCode": "Pincode/Zipcode of the printing company's address", "addressLine":"Address Line"},
     * "paypal":"Paypal email address",
     * "ltime":"Lead time for an order completion",
     * "price":"Default price for card printing (for cards uploaded by users)",
     * "phone":"Phone number"
     * //Below are optional
     * "description": "Seller's company's description",
     * "name":"Name used on the dashboard"     *
     * }
     * @apiError (404) - Email Address is not registered
     * @apiError (401) - OTP is wrong
     * @apiError (400) - Invalid data.
     */
    @RequestMapping(value = "/OTP", method = RequestMethod.POST)
    public void CheckOTP(@RequestBody RUser rUser, HttpServletResponse response) throws SQLException {
        String id = rUser.getId();
        User user = userDAO.get(id);
        if (user == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else if (user.getOtp() != rUser.getOtp()) response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        else {
            switch (user.getType()) {
                case SELLER_TYPE:
                    RAddress rAddress = rUser.getAddress();
                    Address ad = new Address();
                    addressService.update(rAddress, ad);
                    if (!addressService.isValid(ad)) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                    Seller seller = sellerDAO.get(id);
                    sellerService.update(rUser, seller);
                    seller.setAddress(ad);
                    if (!sellerService.isValid(seller)) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    seller.setActivated(true);
                    sellerDAO.update(seller);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    /**
     * @api {get} /user/resetPassword/ Password token
     * @apiDescription Generate token for password resetting. This token will be sent to the provided email address.
     * @apiGroup Auth
     * @apiParam {String} id Email Address
     * @apiError (503) - Email Service is not working.
     * @apiError (404) - Email id not found.
     */
    @RequestMapping("/resetPassword")
    public void resetPass(@RequestParam String id, HttpServletResponse response) {
        User user = userDAO.get(id);
        if (user != null) {
            String token = GeneralHelper.randomToken();
            PassToken passToken = passTokenDAO.get(id);
            if (passToken == null)
                passToken = new PassToken(id, token);
            else passToken.setToken(token);

            passToken = passTokenDAO.update(passToken);

            if (!emailHelper.changePassLink(id, token)) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                passTokenDAO.delete(passToken);
            }
        } else
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * @api {post} /user/resetPassword/ Reset password
     * @apiDescription Reset Password
     * @apiGroup Auth
     * @apiParam {String} id Email Address
     * @apiParam {String} token password token
     * @apiParam {String} password New password
     * @apiError (401) - Wrong password token provided
     * @apiError (404) - No user with such email address
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public void resetPassP(@RequestBody RPasswordToken token, HttpServletResponse response) {
        PassToken passToken = passTokenDAO.get(token.getId());
        if (passToken == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else if (!passToken.getToken().equals(token.getToken()))
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        else {
            User user = userDAO.get(token.getId());
            user.setPass(encodeString(token.getPassword()));
            userDAO.update(user);
            passTokenDAO.delete(passToken);
        }

    }

}
