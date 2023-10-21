package com.example.springboottls.controllers;

import com.example.springboottls.IntegrationsTestConfiguration;
import com.example.springboottls.dto.customerDto.CustomerCreateEditDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = IntegrationsTestConfiguration.class)
@AutoConfigureMockMvc
@Transactional
class CustomerControllerTest {
    private static final Long CUSTOMER_ID = 1L;
    private static final Long NOT_EXISTING_ID = 100L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllCustomers() throws Exception {
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk());
    }

    @Test
    void findCustomerById() throws Exception {
        mockMvc.perform(get("/api/v1/customers/{CUSTOMER_ID}", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CUSTOMER_ID))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"))
                .andExpect(jsonPath("$.companyName").value("IvanCorp"))
                .andExpect(jsonPath("$.age").value(18));
    }

    @Test
    void findNotExistingCustomer() throws Exception {
        mockMvc.perform(get("/api/v1/customers/{NOT_EXISTING_ID}", NOT_EXISTING_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());

    }

    @Test
    void create() throws Exception {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .name("test")
                .email("test@mail.ru")
                .age(19)
                .companyName("IvanCorp")
                .build();
        String requestJson = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("test@mail.ru"))
                .andExpect(jsonPath("$.companyName").value("IvanCorp"))
                .andExpect(jsonPath("$.age").value(19));
    }

    @Test
    void createWithNotExistingCompany() throws Exception {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .name("test")
                .email("test@mail.ru")
                .age(19)
                .companyName("error")
                .build();
        String requestJson = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createWithMissingName() throws Exception {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .email("test@mail.ru")
                .age(19)
                .build();
        String requestJson = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createWithMissingEmail() throws Exception {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .name("test")
                .age(19)
                .build();
        String requestJson = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createWithMissingAge() throws Exception {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .name("test")
                .email("test@mail.ru")
                .build();
        String requestJson = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createWithIncorrectEmail() throws Exception {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .name("test")
                .email("incorrect email")
                .age(19)
                .build();
        String requestJson = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void update() throws Exception {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .name("test")
                .email("test@mail.ru")
                .companyName("IvanCorp")
                .age(19)
                .build();
        String requestJson = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(put("/api/v1/customers/{CUSTOMER_ID}", CUSTOMER_ID)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CUSTOMER_ID))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("test@mail.ru"))
                .andExpect(jsonPath("$.age").value(19));
    }

    @Test
    void updateNotExistingCustomer() throws Exception {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .name("test")
                .email("test@mail.ru")
                .age(19)
                .build();
        String requestJson = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(put("/api/v1/customers/{NOT_EXISTING_ID}", NOT_EXISTING_ID)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/{CUSTOMER_ID}", CUSTOMER_ID))
                .andExpect(status().isOk());
    }
}