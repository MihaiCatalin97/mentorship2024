package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.user.client.SessionFeignClient;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.SessionWithKey;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionController.class)
class SessionControllerTest extends AbstractControllerTest {

    @Autowired
    private SessionFeignClient sessionFeignClient;

    @Test
    void createSessionShouldReturnDataFromFeign() throws Exception {
        when(sessionFeignClient.createSession(any(LoginRequest.class)))
                .thenReturn(SessionWithKey.builder()
                        .userId(123).build());

        mockMvc.perform(post("/sessions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                LoginRequest.builder()
                                        .email("email@email.com")
                                        .password("password")
                                        .build()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(123));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void getSessionShouldReturnDataFromFeign(final String sessionHeader) throws Exception {
        mockMvc.perform(get(String.format("/sessions/%s", sessionHeader))
                        .header("X-SESSION", sessionHeader)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getSessionsShouldReturnDataFromFeignWhenAdmin() throws Exception {
        when(sessionFeignClient.find(any(), any())).thenReturn(
                Collections.singletonList(
                        Session.builder()
                                .userId(1)
                                .build()
                )
        );

        mockMvc.perform(get("/sessions")
                        .header("X-SESSION", ADMIN_HEADER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    void getSessionsShouldReturnUnauthorizedWhenUser() throws Exception {
        mockMvc.perform(get("/sessions")
                        .header("X-SESSION", USER_HEADER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}

