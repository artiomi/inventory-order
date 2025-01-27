package io.artiomi.inventory.svc.adapter.in.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.artiomi.inventory.api.contract.model.AcquireRequestApi;
import io.artiomi.inventory.api.contract.model.InventoryItemApi;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@Testcontainers
@SpringBootTest(webEnvironment = DEFINED_PORT)
@AutoConfigureMockMvc
class InventoryApiAdapterIT {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> pgDBContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:12.22"));
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


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
    class InventoriesSearchTest {

        @Test
        void shouldReturnAllInventoriesOnNoFilters() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(3)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldReturnSingleInventoryOnNameFilter() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("name", "Short table"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldReturnSingleInventoryOnIdFilter() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("id", "TBL_SH_1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldReturnEmptyListIfNoInventoryMatchFilter() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("id", "unknown"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Nested
    @Sql(scripts = {"classpath:clean-tables.sql", "classpath:insert-test-data.sql"})
    class InventoriesCreateTest {
        @Test
        void shouldCreateNewInventory() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("id", "test"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
                    .andExpect(MockMvcResultMatchers.status().isOk());

            InventoryItemApi itemApi = new InventoryItemApi("test", "test create", 5);

            mockMvc.perform(MockMvcRequestBuilders.post("/inventories/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemApi)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("test"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldUpdateExistingInventory() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("id", "TBL_SH_1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].availableCount").value(12))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            InventoryItemApi itemApi = new InventoryItemApi("TBL_SH_1", "test create", 100);

            mockMvc.perform(MockMvcRequestBuilders.post("/inventories/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemApi)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.availableCount").value(100))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Nested
    @Sql(scripts = {"classpath:clean-tables.sql", "classpath:insert-test-data.sql"})
    class InventoriesAcquireTest {

        @Test
        void shouldAcquireInventorySuccessfully() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("id", "TBL_SH_1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].availableCount").value(12))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            AcquireRequestApi request = new AcquireRequestApi(3);

            mockMvc.perform(MockMvcRequestBuilders.put("/inventories/{id}/acquire/", "TBL_SH_1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.availableCount").value(9))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void shouldFailToAcquireInventoryIfCountIsGreaterThanAvailableValue() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("id", "TBL_SH_1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].availableCount").value(12))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            AcquireRequestApi request = new AcquireRequestApi(15);

            mockMvc.perform(MockMvcRequestBuilders.put("/inventories/{id}/acquire/", "TBL_SH_1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        void shouldFailToAcquireInventoryIfNotFoundValue() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("id", "unknown"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
                    .andExpect(MockMvcResultMatchers.status().isOk());

            AcquireRequestApi request = new AcquireRequestApi(15);

            mockMvc.perform(MockMvcRequestBuilders.put("/inventories/{id}/acquire/", "unknown")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }
    }

    @Nested
    @Sql(scripts = {"classpath:clean-tables.sql", "classpath:insert-test-data.sql"})
    class InventoriesDeleteTest {

        @Test
        void shouldDeleteInventorySuccessfully() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("id", "TBL_SH_1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            mockMvc.perform(MockMvcRequestBuilders.delete("/inventories/{id}/", "TBL_SH_1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());

            mockMvc.perform(MockMvcRequestBuilders.get("/inventories/").param("id", "TBL_SH_1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }


    }

}