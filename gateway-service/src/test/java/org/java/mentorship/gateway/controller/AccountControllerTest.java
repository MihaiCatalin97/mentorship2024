package org.java.mentorship.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.budget.client.AccountFeignClient;
import org.java.mentorship.contracts.budget.dto.Account;
import org.java.mentorship.contracts.budget.dto.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountFeignClient accountFeignClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccounts() throws Exception {
        Account account1 = Account.builder().id(1).userId(1).name("Account 1").type(AccountType.CHECKING).balance(100).currency("USD").build();
        Account account2 = Account.builder().id(2).userId(1).name("Account 2").type(AccountType.SAVINGS).balance(200).currency("USD").build();
        when(accountFeignClient.getAccounts()).thenReturn(Arrays.asList(account1, account2));

        mockMvc.perform(get("/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Account 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Account 2"));
    }

    @Test
    void testGetAccountById() throws Exception {
        Account account = Account.builder().id(1).userId(1).name("Account 1").type(AccountType.CHECKING).balance(100).currency("USD").build();
        when(accountFeignClient.getAccountById(anyInt())).thenReturn(account);

        mockMvc.perform(get("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Account 1"));
    }

    @Test
    void testCreateAccount() throws Exception {
        Account account = Account.builder().id(1).userId(1).name("Account 1").type(AccountType.CHECKING).balance(100).currency("USD").build();
        when(accountFeignClient.createAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Account 1"));
    }

    @Test
    void testUpdateAccount() throws Exception {
        Account account = Account.builder().id(1).userId(1).name("Updated Account").type(AccountType.SAVINGS).balance(150).currency("USD").build();
        when(accountFeignClient.updateAccount(anyInt(), any(Account.class))).thenReturn(account);

        mockMvc.perform(put("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Account"));
    }

    @Test
    void testDeleteAccount() throws Exception {
        mockMvc.perform(delete("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
