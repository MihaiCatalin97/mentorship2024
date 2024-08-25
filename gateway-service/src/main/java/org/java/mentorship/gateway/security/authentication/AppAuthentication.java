package org.java.mentorship.gateway.security.authentication;

import lombok.AllArgsConstructor;
import org.java.mentorship.contracts.user.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class AppAuthentication implements Authentication {

    private final String name;
    private final Object credentials;
    private final Object principal;
    private final User details;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated;

    public AppAuthentication(User user) {
        this.name = user.getEmail();
        this.credentials = new Object();
        this.principal = new Object();
        this.details = user;
        this.authorities = new ArrayList<>();
        this.authenticated = true;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public User getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return name;
    }
}
