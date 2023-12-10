package ru.vanek.task_management_application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.vanek.task_management_application.repositories.TasksRepository;
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TasksRepository tasksRepository;
    @Container
    private static final PostgreSQLContainer<?> postgreSQL=
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.1"));
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQL::getUsername);
        registry.add("spring.datasource.password", postgreSQL::getPassword);
        registry.add("spring.jpa.generate-ddl=", ()->true);
    }
    @Test
    @SneakyThrows
    void getAllTasks() {
        var result =mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/tasks"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(new ObjectMapper().writeValueAsString(tasksRepository.findAll()));
    }

}