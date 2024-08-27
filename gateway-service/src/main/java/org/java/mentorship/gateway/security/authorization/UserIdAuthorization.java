package org.java.mentorship.gateway.security.authorization;

import lombok.experimental.UtilityClass;
import org.java.mentorship.gateway.exception.domain.UnauthorizedException;
import org.java.mentorship.gateway.security.authentication.AppAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@UtilityClass
public class UserIdAuthorization {

    public static void loggedInAsUser(Integer userId) {
        AppAuthentication authentication = (AppAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new UnauthorizedException("Unauthorized");
        }
        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized");
        }
        if (!isAdmin(authentication) && !Objects.equals(userId, authentication.getDetails().getId())) {
            throw new UnauthorizedException("You are not authorized to access this resource");
        }
    }

    public static void loggedInAsAnyUser() {
        AppAuthentication authentication = (AppAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication))
            throw new UnauthorizedException("Unauthorized");
        if (!authentication.isAuthenticated())
            throw new UnauthorizedException("Unauthorized");
    }

    public static <T> List<T> filterResults(final List<T> entriesToFilter,
                                            final Function<T, Integer> userIdFunction) {
        AppAuthentication authentication = (AppAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if (isAdmin(authentication)) {
            return entriesToFilter;
        }

        Integer userId = authentication.getDetails()
                .getId();

        return entriesToFilter.stream()
                .filter(entry -> userIdFunction.apply(entry)
                        .equals(userId))
                .toList();
    }

    public static <T> Optional<T> filterResult(final T entryToFilter,
                                               final Function<T, Integer> userIdFunction) {
        AppAuthentication authentication = (AppAuthentication) SecurityContextHolder.getContext().getAuthentication();

        Integer userId = authentication.getDetails()
                .getId();

        if (isAdmin(authentication) ||
                userIdFunction.apply(entryToFilter)
                        .equals(userId)) {
            return Optional.of(entryToFilter);
        }

        return Optional.empty();
    }

    private static boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals("ADMIN"));
    }
}
