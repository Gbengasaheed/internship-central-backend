package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IUserDetails implements UserDetails, Serializable {
    private long id;
    private String email;
    private String phoneNo;
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    private Collection<IAuthorities> authorities = Collections.emptyList();
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled;

    public static long getId(Authentication authentication) {
        IUserDetails iUserDetails = (IUserDetails) authentication.getPrincipal();
        return iUserDetails.getId();
    }

    public User toUser() {
        return new User(this.email, null, this.authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getUsersname(){
        return username;
    }
}
