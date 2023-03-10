package com.example.bookstoreapp.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class TokenGenerator {
    private static final String TOKEN_SECRET = "Ashuvarusoju";

    public String createToken(int id) {
        try {
            //to set algorithm
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            String token = JWT.create().withClaim("user_id", id).sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            //log Token Signing Failed
        } catch (IllegalArgumentException e) {
       // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public int decodeToken(String token) {
        int userid;
     //for verification algorithm
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException e) {
   // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert verification != null;
        JWTVerifier jwtverifier = verification.build();
       //to decode token
        DecodedJWT decodedjwt = jwtverifier.verify(token);

        Claim claim = decodedjwt.getClaim("user_id");
        userid = claim.asInt();
        return userid;

    }
}
