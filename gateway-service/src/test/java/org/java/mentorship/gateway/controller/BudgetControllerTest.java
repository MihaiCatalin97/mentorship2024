package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.budget.client.BudgetFeignClient;
import org.java.mentorship.contracts.budget.dto.Budget;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BudgetController.class)
class BudgetControllerTest extends AbstractControllerTest {

    @MockBean
    private BudgetFeignClient budgetFeignClient;

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void getBudgets_ShouldReturnListOfBudgets(final String sessionHeader) throws Exception {
        List<Budget> mockBudgets = Arrays.asList(
                new Budget(1, 123, "Budget 1", 1000, null, 1, 1),
                new Budget(2, 123, "Budget 2", 2000, null, 2, 2)
        );
        when(budgetFeignClient.getBudgets()).thenReturn(mockBudgets);

        mockMvc.perform(get("/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Budget 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Budget 2"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void getBudgetById_ShouldReturnBudget_WhenBudgetExists(final String sessionHeader) throws Exception {
        Budget mockBudget = new Budget(1, 123, "Budget 1", 1000, null, 1, 1);
        when(budgetFeignClient.getBudgetById(1)).thenReturn(mockBudget);

        mockMvc.perform(get("/budgets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Budget 1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void createBudget_ShouldReturnCreatedBudget(final String sessionHeader) throws Exception {
        Budget budget = new Budget(1, 123, "Budget 1", 1000, null, 1, 1);
        when(budgetFeignClient.createBudget(any(Budget.class))).thenReturn(budget);

        mockMvc.perform(post("/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(budget))
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Budget 1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void updateBudget_ShouldReturnUpdatedBudget(final String sessionHeader) throws Exception {
        Budget budgetToUpdate = new Budget(1, 123, "Updated Budget", 1500, null, 1, 1);
        when(budgetFeignClient.updateBudget(eq(1), any(Budget.class))).thenReturn(budgetToUpdate);

        mockMvc.perform(put("/budgets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(budgetToUpdate))
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Budget"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void deleteBudget_ShouldReturnNoContent(final String sessionHeader) throws Exception {
        Budget mockBudget = new Budget(1, 123, "Budget 1", 1000, null, 1, 1);
        when(budgetFeignClient.getBudgetById(1)).thenReturn(mockBudget);

        mockMvc.perform(delete("/budgets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isNoContent());
    }
}
