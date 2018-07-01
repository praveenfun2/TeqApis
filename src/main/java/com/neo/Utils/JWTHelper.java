package com.neo.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.neo.DatabaseModel.Users.User;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

/**
 * Created by Praveen Gupta on 5/28/2017.
 */
public class JWTHelper {

    private static String secret = "mySecret", issuer = "Teqnicard";

    public static String createToken(String username, String password) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer(issuer)
                .withClaim("username", username)
                .withClaim("password", password)
                .sign(algorithm);
        return token;
    }

    public static User decodeToken(String token) {
        if(!isNotEmpty(token)) return null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            DecodedJWT decodedJWT=verifier.verify(token);
            Map<String, Claim> map=decodedJWT.getClaims();
            String username=map.get("username").asString();
            String password=map.get("password").asString();
            if(username==null || password==null) throw new JWTVerificationException("message");
            return new User(username, password);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
