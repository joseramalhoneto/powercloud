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
import powercloud.model.Area;
import powercloud.model.Department;
import powercloud.service.AreaService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AreaController.class)
class AreaControllerTest {

    private Area area, area2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AreaService service;

    @BeforeEach
    void setUp() {
        area  = new Area(100L, "name_area_test", "description_test","Berlin","color_test");
        area2 = new Area(200L, "name_area_test", "description_test","Berlin","color_test");
    }

    @Test
    void findAll() throws Exception {
        when(service
                .findAll())
                .thenReturn(List.of(area, area2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/area"))
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
                .thenReturn(area);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/area/{id}", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("name_area_test"));
    }

    @Test
    void save() throws Exception {
        when(service.save(any()))
                .thenReturn(area);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/area")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(area))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("name_area_test"))
                .andExpect(jsonPath("$.description").value("description_test"));
    }

    @Test
    void update()  throws Exception {
        when(service.update(any()))
                .thenReturn(area);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/area")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(area))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("name_area_test"))
                .andExpect(jsonPath("$.description").value("description_test"));
    }

    @Test
    void deleteById() throws Exception {
        when(service.save(any()))
                .thenReturn(area);

        this.mockMvc.perform(delete("/area/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getRevenueById() throws Exception {
        Department department = new Department(200L, "Contact Manager Test 1", 4000, 14);
        Department department2 = new Department(300L, "Contact Manager Test 2", 16000, 5);
        area.setDepartments(List.of(department, department2));

        when(service.getRevenueById(100L))
                .thenReturn(20000.0);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/area/revenue/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getDepartmentsById() throws Exception {
        Department department = new Department(200L, "Contact Manager Test 1", 4000, 14);
        Department department2 = new Department(300L, "Contact Manager Test 2", 16000, 5);
        area.setDepartments(List.of(department, department2));

        when(service.getDepartmentsById(100L))
                .thenReturn(List.of(department, department2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/area/departments/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
