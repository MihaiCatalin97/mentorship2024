package org.java.mentorship.gateway.controller;

import org.java.mentorship.contracts.budget.client.CategoryFeignClient;
import org.java.mentorship.contracts.budget.dto.Category;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest extends AbstractControllerTest {

    @MockBean
    private CategoryFeignClient categoryFeignClient;

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testGetCategories(final String sessionHeader) throws Exception {
        Category category1 = Category.builder().id(1).name("Category 1").userId(123).build();
        Category category2 = Category.builder().id(2).name("Category 2").userId(123).build();

        when(categoryFeignClient.getCategories()).thenReturn(Arrays.asList(category1, category2));

        mockMvc.perform(get("/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testGetCategoryById(final String sessionHeader) throws Exception {
        Category category = Category.builder().id(1).name("Category 1").userId(123).build();

        when(categoryFeignClient.getCategoryById(anyInt())).thenReturn(category);

        mockMvc.perform(get("/categories/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category 1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testCreateCategory(final String sessionHeader) throws Exception {
        Category category = Category.builder().id(1).name("Category 1").userId(123).build();

        when(categoryFeignClient.createCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category))
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category 1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testUpdateCategory(final String sessionHeader) throws Exception {
        Category category = Category.builder().id(1).name("Category 1").userId(123).build();

        when(categoryFeignClient.updateCategory(anyInt(), any(Category.class))).thenReturn(category);

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category))
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category 1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {USER_HEADER, ADMIN_HEADER})
    void testDeleteCategory(final String sessionHeader) throws Exception {
        Category category = Category.builder().id(1).name("Category 1").userId(123).build();
        when(categoryFeignClient.getCategoryById(anyInt())).thenReturn(category);

        mockMvc.perform(delete("/categories/1")
                        .header("X-SESSION", sessionHeader))
                .andExpect(status().isNoContent());
    }
}
