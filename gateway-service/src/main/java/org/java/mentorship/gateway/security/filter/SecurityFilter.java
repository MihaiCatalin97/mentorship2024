package org.java.mentorship.gateway.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.java.mentorship.contracts.user.client.SessionFeignClient;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.gateway.security.authentication.AppAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final SessionFeignClient sessionClient;
    private final UserFeignClient userClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String sessionHeader = request.getHeader("X-SESSION");

        SecurityContextHolder.getContext().setAuthentication(null);

        if (sessionHeader == null || sessionHeader.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // Validate the session
            Session session = sessionClient.getSession(sessionHeader);
            if (session == null) {
                chain.doFilter(request, response);
                return;
            }
            // Retrieve user details using the session
            User user = userClient.getUser(session.getUserId());
            AppAuthentication auth = new AppAuthentication(user);

            // Set the authentication in the context
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

}