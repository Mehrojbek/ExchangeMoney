package uz.pdp.appexchangemoney.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    String secretKey="secretWord";


    public String generateToken(String username){
        Long expireDate=10_000_000l;
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireDate))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }


    public boolean validateToken(String token){
       try {
           Jwts
                   .parser()
                   .setSigningKey(secretKey)
                   .parseClaimsJws(token);
           return true;
       }catch (Exception e){
           e.printStackTrace();
       }
        return false;
    }


    public String getUsername(String token){
        String username = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }
}
