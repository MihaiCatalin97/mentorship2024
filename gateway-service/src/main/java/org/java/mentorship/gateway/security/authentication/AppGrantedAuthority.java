package org.java.mentorship.gateway.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class AppGrantedAuthority implements GrantedAuthority {

    private final String name;

    @Override
    public String getAuthority() {
        return name;
    }

}

