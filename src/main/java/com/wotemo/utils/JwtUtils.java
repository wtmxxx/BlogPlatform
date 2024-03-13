package com.wotemo.utils;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class JwtUtils {
    private String SECRET_KEY;

    // SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(),"HS256");

    public String generateJWT(String id, String username, long exp) {
//        SecretKey genKey = Jwts.SIG.HS256.key().build();
//        String key = Base64.getEncoder().encodeToString(genKey.getEncoded());

//        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
//        SecretKey key = new SecretKeySpec(decodedKey, "HS256");


//        System.out.println(genKey);
        Map<String, Object> claims = new HashMap<>();
//        claims.put("id", id);
        claims.put("username", username);
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("alg", "HS256");
//        headers.put("typ", "JWT");
        String jwt = Jwts.builder()
                .subject(id)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + exp))
                .issuedAt(new Date())
                .issuer("BlogPlatform")
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
//                .header(headers)
//                .and()
                .compact();
        return jwt;
    }

    public Jwt parseJWT(String jwt) {
//        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
//        SecretKey secretKey = new SecretKeySpec(decodedKey, "HS256");
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parse(jwt);
//        Jws<Claims> JwsClaims = Jwts.parser().decryptWith(secretKey).build().parseSignedClaims(jwt);
//        Claims claims = JwsClaims.getPayload();
//        return claims;
    }
}
