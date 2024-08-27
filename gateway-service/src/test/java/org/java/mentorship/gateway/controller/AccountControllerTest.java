package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.budget.client.AccountFeignClient;
import org.java.mentorship.contracts.budget.dto.Account;
import org.java.mentorship.contracts.budget.dto.AccountType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest extends AbstractControllerTest {

    @MockBean
    private AccountFeignClient accountFeignClient;

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testGetAccounts(final String sessionHeader) throws Exception {
        Account account1 = Account.builder().id(1).userId(123).name("Account 1").type(AccountType.CHECKING).balance(100).currency("USD").build();
        Account account2 = Account.builder().id(2).userId(123).name("Account 2").type(AccountType.SAVINGS).balance(200).currency("USD").build();

        when(accountFeignClient.getAccounts()).thenReturn(Arrays.asList(account1, account2));

        mockMvc.perform(get("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Account 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Account 2"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testGetAccountById(final String sessionHeader) throws Exception {
        Account account = Account.builder().id(1).userId(123).name("Account 1").type(AccountType.CHECKING).balance(100).currency("USD").build();
        when(accountFeignClient.getAccountById(1)).thenReturn(account);

        mockMvc.perform(get("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Account 1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testCreateAccount(final String sessionHeader) throws Exception {
        Account account = Account.builder().id(1).userId(123).name("Account 1").type(AccountType.CHECKING).balance(100).currency("USD").build();
        when(accountFeignClient.createAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account))
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Account 1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testUpdateAccount(final String sessionHeader) throws Exception {
        Account account = Account.builder().id(1).userId(123).name("Updated Account").type(AccountType.SAVINGS).balance(150).currency("USD").build();
        when(accountFeignClient.updateAccount(eq(1), any(Account.class))).thenReturn(account);

        mockMvc.perform(put("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account))
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Account"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testDeleteAccount(final String sessionHeader) throws Exception {
        Account account = Account.builder().id(1).userId(123).name("Account 1").type(AccountType.CHECKING).balance(100).currency("USD").build();
        when(accountFeignClient.getAccountById(1)).thenReturn(account);

        mockMvc.perform(delete("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isNoContent());
    }
}
