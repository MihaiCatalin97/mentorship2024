package org.java.mentorship.gateway.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.java.mentorship.contracts.user.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Getter
public class AppAuthentication implements Authentication {

    private final String name;
    private final Object credentials;
    private final Object principal;
    private final User details;
    private final Collection<AppGrantedAuthority> authorities;

    @Setter
    private boolean authenticated;

    public AppAuthentication(User user) {
        this.name = user.getEmail();
        this.credentials = new Object();
        this.principal = new Object();
        this.details = user;
        this.authorities = new ArrayList<>();
        this.authenticated = true;
        if (user.getIsAdmin())
            this.authorities.add(new AppGrantedAuthority("ADMIN"));
    }

}