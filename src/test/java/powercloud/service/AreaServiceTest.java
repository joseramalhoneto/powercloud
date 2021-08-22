package powercloud.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import powercloud.model.Area;
import powercloud.model.SubArea;
import powercloud.repository.AreaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AreaServiceTest {

    private SubArea subArea, subArea2;
    private List<SubArea> subareas;
    private Area area, area2;

    @Autowired
    private AreaService service;

    @Autowired
    private AreaRepository repository;

    public AreaServiceTest() {
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
    void findAll() {
    }

    @Test
    void findById() {
        setSubareas();
        setArea();
        repository.save(area);

        Optional<Area> areaResult = repository.findById(100L);
        assertEquals(true, areaResult.isPresent());
        assertEquals(100L, areaResult.get().getId());
    }

    @Test
    void save() {
        setSubareas();
        setArea();
        repository.save(area);

        boolean exists = repository.existsById(100L);
        assertEquals(true, exists);
    }

    @Test
    void update() {
    }

    @Test
    void saveAll() {
        setSubareas();
        area  = new Area(100L, "name_area_test", "description_test", "color_test", subareas);
        area2 = new Area(200L, "name_area_test 2", "description_test 2", "color_test 2", subareas);
        service.saveAll(List.of(area, area2));

        boolean exists = repository.existsById(100L);
        assertEquals(true, exists);

        exists = repository.existsById(200L);
        assertEquals(true, exists);
    }

    @Test
    void deleteAll() {
        setSubareas();
        area  = new Area(100L, "name_area_test", "description_test", "color_test", subareas);
        area2 = new Area(200L, "name_area_test 2", "description_test 2", "color_test 2", subareas);
        service.saveAll(List.of(area, area2));

        service.deleteAll();
        boolean exists = repository.existsById(100L);
        assertEquals(false, exists);

        exists = repository.existsById(200L);
        assertEquals(false, exists);
    }

    @Test
    void deleteById() {
        setSubareas();
        setArea();
        service.save(area);

        service.deleteById(area.getId());
        boolean exists = repository.existsById(100L);
        assertEquals(false, exists);
    }

    @Test
    void getMaxRevenue() {
    }

    @Test
    void getMinRevenue() {
    }
}