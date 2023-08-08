package com.example.demo.models;

import com.example.demo.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = Constants.EntityNames.AUTHORITIES)
public class IAuthorities implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public static IAuthorities instance(String username, String authority) {
        IAuthorities authorities = new IAuthorities();
        authorities.setAuthority(authority);
        authorities.setEmail(username);
        return authorities;
    }
}
