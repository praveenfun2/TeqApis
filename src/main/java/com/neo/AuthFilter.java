package com.neo;

import com.neo.DAO.*;
import com.neo.DatabaseModel.Users.User;
import com.neo.Utils.GeneralHelper;
import com.neo.Utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Praveen Gupta on 5/28/2017.
 */
public class AuthFilter implements Filter {

    public static String AUTHENTICATION_HEADER = "Authorization";

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

            String method=httpServletRequest.getMethod();
            if(!method.equalsIgnoreCase("GET") && !method.equalsIgnoreCase("POST")){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            String[] t = httpServletRequest.getServletPath().split("/");

            if (t.length < 2) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            String key = t[1];
            if (!key.equals("admin") && !key.equals("customer") && !key.equals("seller") && !key.equals("courier")) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            String authToken = httpServletRequest.getHeader(AUTHENTICATION_HEADER);
            User user = JWTHelper.decodeToken(authToken);
            if (user == null) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String username = user.getId(), pass = user.getPass();
            user = userDAO.get(username);
            if (user == null || !user.getPass().equals(GeneralHelper.encodeString(pass))) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            boolean b = true;
            switch (key) {
                case "admin":
                    b = user.getType().equals(User.ADMIN_TYPE);
                    break;
                case "seller":
                    b = user.getType().equals(User.SELLER_TYPE);
                    break;
                case "customer":
                    b = user.getType().equals(User.CUSTOMER_TYPE);
                    break;
                case "courier":
                    b = user.getType().equals(User.COURIER_TYPE);
                    break;
            }

            if (b) {
                httpServletRequest.setAttribute("id", user.getId());
                filterChain.doFilter(servletRequest, servletResponse);
            } else httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {

    }
}
