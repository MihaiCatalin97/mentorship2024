package org.java.mentorship.gateway.security.authorization;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.gateway.exception.domain.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@UtilityClass
public class UserIdAuthorization {

    public static void loggedInAsUser(Integer userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())
            throw new UnauthorizedException("Unauthorized");
        if (!Objects.equals(userId, ((User) authentication.getDetails()).getId()))
            throw new UnauthorizedException("You are not authorized to access this resource");
    }

}
