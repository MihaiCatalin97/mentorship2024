package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.java.mentorship.contracts.user.dto.request.SendVerificationTokenRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends AbstractControllerTest {
    @Autowired
    private UserFeignClient userFeignClient;

    @Test
    void getUsersShouldReturnDataFromFeignWhenAdmin() throws Exception {
        when(userFeignClient.getUsers())
                .thenReturn(Collections.singletonList(new User()));

        mockMvc.perform(get("/users")
                        .header("X-SESSION", ADMIN_HEADER))
                .andExpect(status().isOk());
    }

    @Test
    void getUsersShouldReturnUnauthorizedWhenUser() throws Exception {
        mockMvc.perform(get("/users")
                        .header("X-SESSION", USER_HEADER))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void getUserByIdShouldReturnDataFromFeign(final String sessionHeader) throws Exception {
        mockMvc.perform(get("/users/123")
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk());
    }

    @Test
    void registerUserShouldReturnDataFromFeign() throws Exception {
        when(userFeignClient.registerUser(any(RegistrationRequest.class)))
                .thenReturn(new User());


        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        RegistrationRequest.builder()
                                                .email("email@email.com")
                                                .firstName("First name")
                                                .lastName("Last name")
                                                .password("Password")
                                                .build()
                                )
                        )
                )
                .andExpect(status().isOk());
    }

    @Test
    void verifyUserShouldReturnDataFromFeign() throws Exception {
        when(userFeignClient.verifyUser(anyString()))
                .thenReturn(true);

        mockMvc.perform(put("/users/verify/AAA"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void sendNotificationTokenShouldCallFeign(final String sessionHeader) throws Exception {
        when(userFeignClient.sendNotificationToken(any(SendVerificationTokenRequest.class)))
                .thenReturn(true);

        mockMvc.perform(post("/users/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader)
                        .content(
                                objectMapper.writeValueAsString(
                                        SendVerificationTokenRequest.builder()
                                                .userId(123)
                                                .build()
                                )
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

}
