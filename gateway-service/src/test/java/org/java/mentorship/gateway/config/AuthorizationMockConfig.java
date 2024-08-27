package org.java.mentorship.gateway.config;

import org.java.mentorship.contracts.user.client.SessionFeignClient;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.User;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.OffsetDateTime;

@Configuration
@Import(SecurityConfig.class)
public class AuthorizationMockConfig {

    @MockBean
    private SessionFeignClient sessionFeignClient;

    @MockBean
    private UserFeignClient userFeignClient;

    public void mockSession(String sessionId, Integer userId) {
        mockSession(sessionId, userId, false);
    }

    public void mockSession(String sessionId, Integer userId, boolean isAdmin) {
        Mockito.when(sessionFeignClient.getSession(sessionId))
                .thenReturn(Session.builder()
                        .userId(userId)
                        .expiresAt(OffsetDateTime.MAX)
                        .build());

        Mockito.when(userFeignClient.getUser(userId))
                .thenReturn(User.builder()
                        .id(userId)
                        .isAdmin(isAdmin)
                        .build());
    }
}
