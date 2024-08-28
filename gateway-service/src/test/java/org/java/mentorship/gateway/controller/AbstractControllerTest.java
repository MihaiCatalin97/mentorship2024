package org.java.mentorship.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.java.mentorship.gateway.config.AuthorizationMockConfig;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(AuthorizationMockConfig.class)
public class AbstractControllerTest {

    protected static final String USER_HEADER = "123-USER";

    protected static final String ADMIN_HEADER = "123-ADMIN";

    protected static final Integer USER_ID = 123;

    protected static final Integer ADMIN_ID = 999;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private AuthorizationMockConfig authorizationMockConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorizationMockConfig.mockSession(USER_HEADER, USER_ID);
        authorizationMockConfig.mockSession(ADMIN_HEADER, ADMIN_ID, true);
    }
}
