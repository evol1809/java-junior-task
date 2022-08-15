package com.cometrica.javajuniortask.controller;

import com.cometrica.javajuniortask.dto.ClientDTO;
import com.cometrica.javajuniortask.dto.DebtDTO;
import com.cometrica.javajuniortask.dto.PaymentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class MockEnvIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    @Sql("/delete.sql")
    public void init() {}

    @Test
    @DisplayName("Get clients")
    @Sql("/insert.sql")
    public void getClients() throws Exception {

        this.mockMvc.perform(get("/api/v1/clients"))
//                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Boss")))
                .andExpect(jsonPath("$[0].totalDebt", is(100)));
    }

    @Test
    @DisplayName("First add the payment, then check the total debt of the client")
    @Sql("/insert.sql")
    public void addPaymentGetClient() throws Exception {

        var payment = new PaymentDTO();
        payment.setClientId(UUID.fromString("295abfe0-e436-4c9e-bc71-71066acb6871"));
        payment.setValue(BigDecimal.valueOf(10));

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(payment)))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/api/v1/clients"))
//                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Boss")))
                .andExpect(jsonPath("$[0].totalDebt", is(90)));
    }

    @Test
    @DisplayName("First add the debt, then check the total debt of the client")
    @Sql("/insert.sql")
    public void addDebtGetClient() throws Exception {

        var debt = new DebtDTO();
        debt.setClientId(UUID.fromString("295abfe0-e436-4c9e-bc71-71066acb6871"));
        debt.setValue(BigDecimal.valueOf(10));

        mockMvc.perform(post("/api/v1/debts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(debt)))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/api/v1/clients"))
//                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Boss")))
                .andExpect(jsonPath("$[0].totalDebt", is(110)));
    }

    @Test
    @DisplayName("Create client")
    public void createClient() throws Exception {

        var client = new ClientDTO();
        client.setName("Ivan");

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(client)))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/api/v1/clients"))
//                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Ivan")))
                .andExpect(jsonPath("$[0].totalDebt", is(0)));
    }

    @Test
    @DisplayName("Client creation error")
    public void clientCreationError() throws Exception {

        var client = new ClientDTO();
        client.setName("");

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(client)))
//                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("The client name is null")));
    }

    @Test
    @DisplayName("Debt creation error")
    public void debtCreationError() throws Exception {

        var debt = new DebtDTO();

        mockMvc.perform(post("/api/v1/debts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(debt)))
//                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Value less than 0 or equal to 0 or equal null")));
    }

    @Test
    @DisplayName("Payment creation error")
    public void paymentCreationError() throws Exception {

        var payment = new PaymentDTO();

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(payment)))
//                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Value less than 0 or equal to 0 or equal null")));
    }
}
