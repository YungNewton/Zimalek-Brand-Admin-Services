package com.zmarket.brandadminservice.modules.security.jwt;

import com.zmarket.brandadminservice.modules.security.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Slf4j
@Component
public class CurrentUser implements InitializingBean {

   @Value("${jwt.base64-secret}")
   private String base64Secret;

   private Long id;
   private String firstname;
   private String lastname;
   private String email;
   private String phone;
   private String token;

   private Key key;


   public void setUserDetails(UserDetailsImpl userdetails) {
      this.id = userdetails.getId();
      this.lastname = userdetails.getLastname();
      this.firstname = userdetails.getFirstname();
      this.email = userdetails.getEmail();
      this.phone = userdetails.getPhone();
   }

   @Override
   public void afterPropertiesSet() {
      byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
      this.key = Keys.hmacShaKeyFor(keyBytes);
   }

   public String getEmail() {
      return email;
   }

   public Long getId() {
      return id;
   }

   public String getFirstname() {
      return firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public String getPhone() {
      return phone;
   }

   public String getToken() {
      return token;
   }

   public boolean validateToken(String authToken) {
      try {
         Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
         return true;
      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         log.info("Invalid JWT signature.");
         log.trace("Invalid JWT signature trace: {}", e);
      } catch (ExpiredJwtException e) {
         log.info("Expired JWT token.");
         log.trace("Expired JWT token trace: {}", e);
      } catch (UnsupportedJwtException e) {
         log.info("Unsupported JWT token.");
         log.trace("Unsupported JWT token trace: {}", e);
      } catch (IllegalArgumentException e) {
         log.info("JWT token compact of handler are invalid.");
         log.trace("JWT token compact of handler are invalid trace: {}", e);
      }
      return false;
   }

}
