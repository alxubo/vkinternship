package com.internship.olegchistov.configuration;

import com.internship.olegchistov.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${auth_key}")
    private String SECRET_KEY;

    public String extractUsernameFromToken(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String extractRoleFromToken(String jwtToken) {
        return extractClaim(jwtToken, (claims -> claims.get("role"))).toString();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver // what do we need to get (Claims::getUsername or smth)
    ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateJwtToken(Map<String, Object> extraClaims, User userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (86400000 /* 24h */)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact(); // ?
    }

    //    public String generateJwtToken(UserDetails userDetails) {
//        return generateJwtToken(new HashMap<>(), userDetails);
//    }
//
    public String generateJwtTokenFromUserModel(User userModel) {
        return generateJwtToken(new HashMap<>(Map.of(
                "id", userModel.getId(),
                "role", userModel.getRole()
        )), userModel);
    }

    public boolean isTokenValidChecker(String token, UserDetails userDetails) {
        final String username = extractUsernameFromToken(token);

        if (isTokenExpiredChecker(token)) {
            return false;
        }

        return username.equals(userDetails.getUsername());
    }

    private boolean isTokenExpiredChecker(String token) {
        return extractTokenExpiration(token).before(new Date());
    }

    private Date extractTokenExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
