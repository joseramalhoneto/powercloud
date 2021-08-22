package powercloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import powercloud.model.SubArea;
import powercloud.service.AreaService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AreaController.class)
class AreaControllerTest {

    private SubArea subArea, subArea2;
    private List<SubArea> subareas;
    private Area area, area2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AreaService service;

    public AreaControllerTest() {
        subArea  = new SubArea();
        subArea2 = new SubArea();
        subareas = new ArrayList<>();
        area  = new Area();
        area2  = new Area();
    }

    public void setSubareas() {
        subArea  = new SubArea(200L, "Contact Manager Test 1", 9000, 14);
        subArea2 = new SubArea(201L, "Contact Manager Test 2", 15000, 9);
        subareas.add(subArea);
        subareas.add(subArea2);
    }

    public void setArea() {
        area  = new Area(100L, "name_area_test", "description_test", "color_test", subareas);
    }

    @Test
    void findAll() throws Exception {
        setSubareas();
        area  = new Area(100L, "name_area_test", "description_test", "color_test", subareas);
        area2 = new Area(200L, "name_area_test 2", "description_test 2", "color_test 2", subareas);

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
        setSubareas();
        setArea();

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
    void findByIdInvalid() throws Exception {
        setSubareas();
        setArea();

        when(service
                .findById(999L))
                .thenReturn(area);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/area/{id}", 999L))
                .andDo(print())
                .andExpect(status().isNotFound());
//                .andExpect(jsonPath("$.id").value(100L))
//                .andExpect(jsonPath("$.name").value("name_area_test"));
    }

    @Test
    void findByIdNotFound() throws Exception {
        setSubareas();
        setArea();
        service.save(area);

        service.deleteById(area.getId());
        Area areaResult = service.findById(100L);
        assertEquals(null, areaResult);
    }

    @Test
    void save() throws Exception {
        setSubareas();
        setArea();

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
        setSubareas();
        area = new Area(100L, "new_name_area", "new_description_test", "new_color_test", subareas);

        when(service.update(any()))
                .thenReturn(area);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/area")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(area))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("new_name_area"))
                .andExpect(jsonPath("$.description").value("new_description_test"));
    }

    @Test
    void deleteById() throws Exception {
        setSubareas();
        setArea();

        when(service.save(any()))
                .thenReturn(area);

        this.mockMvc.perform(delete("/area/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getMaxRevenue() {
    }

    @Test
    void getMinRevenue() {
    }
}