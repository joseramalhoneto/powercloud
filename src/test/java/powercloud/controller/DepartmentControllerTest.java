package powercloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import powercloud.model.Department;
import powercloud.service.DepartmentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    private Department department, department2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService service;

    @BeforeEach
    void setUp() {
        this.department =  new Department(100L, "Contact Manager Test 1", 9000, 14);
        this.department2 = new Department(200L, "Contact Manager Test 2", 16000, 5);
    }

    @Test
    void findAll() throws Exception {
        when(service
                .findAll())
                .thenReturn(List.of(department, department2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/department"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(100L))
                .andExpect(jsonPath("$[1].id").value(200L));
    }

    @Test
    void findById() throws Exception {
        when(service
                .findById(100L))
                .thenReturn(department);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/department/{id}", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("Contact Manager Test 1"));
    }

    @Test
    void save() throws Exception {
        when(service.save(any()))
                .thenReturn(department);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(department))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("Contact Manager Test 1"))
                .andExpect(jsonPath("$.revenue").value(9000))
                .andExpect(jsonPath("$.employees").value(14));
    }

    @Test
    void update()  throws Exception {
        when(service.update(any()))
                .thenReturn(department);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(department))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("Contact Manager Test 1"))
                .andExpect(jsonPath("$.revenue").value(9000))
                .andExpect(jsonPath("$.employees").value(14));
    }

    @Test
    void deleteById()   throws Exception {
        when(service.save(any()))
                .thenReturn(department);

        this.mockMvc.perform(delete("/department/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}