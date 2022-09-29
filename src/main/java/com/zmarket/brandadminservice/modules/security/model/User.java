package com.zmarket.brandadminservice.modules.security.model;

import com.zmarket.brandadminservice.modules.security.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class User implements Serializable {

   private Long id;

   private String firstname;

   private String lastname;

   private String email;

   private String phone;

   private Gender gender;

   private boolean enabled;

   private boolean activated;

   private LocalDateTime lastLoginDate;

   private LocalDateTime lastPasswordResetDate;

   private LocalDateTime createdAt;

   private LocalDateTime updatedAt;

   private Set<Authority> authorities;

   public String getFullName() {
      return this.firstname + " " + this.lastname;
   }
}
