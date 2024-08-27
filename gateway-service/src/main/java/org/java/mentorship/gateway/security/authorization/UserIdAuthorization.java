package org.java.mentorship.gateway.security.authorization;

import lombok.experimental.UtilityClass;
import org.java.mentorship.gateway.exception.domain.UnauthorizedException;
import org.java.mentorship.gateway.security.authentication.AppAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@UtilityClass
public class UserIdAuthorization {

    public static void loggedInAsUser(Integer userId) {
        AppAuthentication authentication = (AppAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication))
            throw new UnauthorizedException("Unauthorized");
        if (!authentication.isAuthenticated())
            throw new UnauthorizedException("Unauthorized");
        if (userId == null && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ADMIN")))
            throw new UnauthorizedException("You are not authorized to access this resource");
        if (!Objects.equals(userId, authentication.getDetails().getId()))
            throw new UnauthorizedException("You are not authorized to access this resource");
    }

}
