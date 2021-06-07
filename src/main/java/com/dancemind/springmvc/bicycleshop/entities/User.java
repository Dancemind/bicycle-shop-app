package com.dancemind.springmvc.bicycleshop.entities;


import java.util.Collection;
import java.util.Collections;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.
        SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    String ROLE_PREFIX = "ROLE_";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private final Long id;

    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 100, message = "Name should be from 2 to 100 characters long")
    private final String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 2, max = 300, message = "Password is at least 2 symbols")
    private final String password;

    //@NotEmpty(message = "Full name - enter smth here")
    private String fullname;

    //@NotEmpty(message = "Street - enter smth here")
    private String street;

    //@NotEmpty(message = "City - enter smth here")
    private String city;

    //@NotEmpty(message = "State - enter smth here")
    private String state;

    //@NotEmpty(message = "Postal code - enter smth here")
    private String zip;

    //@NotNull
    private final String role;  // ADMIN or USER

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(ROLE_PREFIX + role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
