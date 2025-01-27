package io.artiomi.order.svc.adapter.in.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.artiomi.inventory.api.client.InventoryApiClient;
import io.artiomi.inventory.api.contract.model.InventoryItemApi;
import io.artiomi.order.api.contract.model.OrderItemUpsertRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestClientException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@Testcontainers
@SpringBootTest(webEnvironment = DEFINED_PORT)
@AutoConfigureMockMvc
class OrderApiAdapterIT {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> pgDBContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:12.22"));
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private InventoryApiClient inventoryApiClient;

    @BeforeAll
    static void beforeAll() {
        pgDBContainer.start();
    }

    @AfterAll
    static void afterAll() {
        pgDBContainer.stop();
    }

    @Nested
    @Sql(scripts = {"classpath:clean-tables.sql", "classpath:insert-test-data.sql"})
    class OrdersSearchTest {

        @Test
        void shouldReturnAllOrdersIfNoFilters() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/orders/"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(3)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldReturnOrderWithGivenId() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/orders/")
                            .param("id", "demo1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value("demo1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldReturnOrderWithGivenName() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/orders/")
                            .param("name", "Long table"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Long table"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldReturnOrderWithGivenInventoryRef() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/orders/")
                            .param("inventoryRef", "TBL_KT_1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].inventoryRef").value("TBL_KT_1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldReturnEmptyListIfNoItemFound() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/orders/")
                            .param("inventoryRef", "unknown"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Nested
    @Sql(scripts = {"classpath:clean-tables.sql", "classpath:insert-test-data.sql"})
    class OrdersUpdateTest {

        @Test
        void shouldUpdateOrderSuccessfully() throws Exception {
            String id = "demo3";
            when(inventoryApiClient.acquire(eq("TBL_KT_1"), argThat(s -> s.getCount() == 30)))
                    .thenReturn(ResponseEntity.ok(new InventoryItemApi()));
            mockMvc.perform(MockMvcRequestBuilders.get("/orders/").param("id", id))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            var request = new OrderItemUpsertRequest("new name", "TBL_KT_1", 30);

            mockMvc.perform(MockMvcRequestBuilders.put("/orders/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.inventoryRef").value(request.getInventoryRef()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.inventoryCount").value(request.getInventoryCount()))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldFailToUpdateOrderOnInvestmentFailure() throws Exception {
            String id = "demo3";
            when(inventoryApiClient.acquire(eq("TBL_KT_1"), argThat(s -> s.getCount() == 30)))
                    .thenThrow(RestClientException.class);
            ResultActions beforeResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/").param("id", id))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            var request = new OrderItemUpsertRequest("new name", "TBL_KT_1", 30);

            mockMvc.perform(MockMvcRequestBuilders.put("/orders/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());

            ResultActions afterResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/").param("id", id))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            assertEquals(beforeResult.andReturn().getResponse().getContentAsString(),
                    afterResult.andReturn().getResponse().getContentAsString());
        }
    }

    @Nested
    @Sql(scripts = {"classpath:clean-tables.sql", "classpath:insert-test-data.sql"})
    class OrdersCreateTest {

        @Test
        void shouldCreateOrderSuccessfully() throws Exception {
            when(inventoryApiClient.acquire(eq("TBL_KT_1"), argThat(s -> s.getCount() == 30)))
                    .thenReturn(ResponseEntity.ok(new InventoryItemApi()));
            mockMvc.perform(MockMvcRequestBuilders.get("/orders/").param("name", "order name"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
                    .andExpect(MockMvcResultMatchers.status().isOk());

            var request = new OrderItemUpsertRequest("order name", "TBL_KT_1", 30);

            mockMvc.perform(MockMvcRequestBuilders.post("/orders/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.inventoryRef").value(request.getInventoryRef()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.inventoryCount").value(request.getInventoryCount()))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        void shouldFailToCreateOrderOnInvestmentFailure() throws Exception {
            when(inventoryApiClient.acquire(eq("TBL_KT_1"), argThat(s -> s.getCount() == 30)))
                    .thenThrow(RestClientException.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/orders/").param("name", "order name"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
                    .andExpect(MockMvcResultMatchers.status().isOk());

            var request = new OrderItemUpsertRequest("order name", "TBL_KT_1", 30);

            mockMvc.perform(MockMvcRequestBuilders.post("/orders/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());

            mockMvc.perform(MockMvcRequestBuilders.get("/orders/").param("name", "order name"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
                    .andExpect(MockMvcResultMatchers.status().isOk());

        }
    }
}