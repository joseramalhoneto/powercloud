package powercloud.service;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import powercloud.exception.AreaInvalidException;
import powercloud.exception.AreaNotFoundException;
import powercloud.model.Area;
import powercloud.model.Department;
import powercloud.repository.AreaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AreaServiceTest {

    private Area area, area2;

    @InjectMocks
    private AreaService service;

    @Mock
    private AreaRepository repository;

    @BeforeEach
    void setUp() {
        service = new AreaService(repository);
        area  = new Area(100L, "name_area_test", "description_test", "Berlin", "color_test");
        area2 = new Area(200L, "name_area_test", "description_test", "Berlin","color_test");
    }

    @Test
    void save() {
        when(repository.save(area)).thenReturn(area);
        Area result = service.save(area);

        assertThat(result).isEqualTo(area);
        verify(repository, times(1)).save(area);
    }

    @Test
    void findAll() {
        List<Area> list = new ArrayList<>();
        list.add(area);
        list.add(area2);

        when(repository.findAll()).thenReturn(list);
        List<Area> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(area.getId())).thenReturn(Optional.of(area));
        Area areaFounded = service.findById(area.getId());

        assertThat(areaFounded).isEqualTo(area);
        assertEquals("name_area_test", areaFounded.getName());
        assertEquals("description_test", areaFounded.getDescription());
        assertEquals("Berlin", areaFounded.getLocation());
        assertEquals("color_test", areaFounded.getColor());
        verify(repository, times(1)).findById(areaFounded.getId());
    }

    @Test
    void update() {
        area = new Area(100L, "name_area_test2", "description_test2", "Offenburg", "color_test2");

        when(repository.existsById(any())).thenReturn(true);
        when(repository.save(area)).thenReturn(area);
        Area areaUpdated = service.update(area);

        assertEquals(100L, areaUpdated.getId());
        assertEquals("name_area_test2", areaUpdated.getName());
        assertEquals("description_test2", areaUpdated.getDescription());
        assertEquals("Offenburg", areaUpdated.getLocation());
        assertEquals("color_test2", areaUpdated.getColor());
        verify(repository, times(1)).save(area);
    }

    @Test
    void saveAll() {
        service.saveAll(List.of(area, area2));

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(argumentCaptor.capture());
        List result = argumentCaptor.getValue();

        assertThat(result).isEqualTo(List.of(area, area2));
    }

    @Test
    void deleteAll() {
        service.saveAll(List.of(area, area2));
        service.deleteAll();

        boolean exists = repository.existsById(100L);
        assertFalse(exists);

        exists = repository.existsById(200L);
        assertFalse(exists);
    }

    @Test
    void deleteById() {
        when(repository.existsById(any())).thenReturn(true);
        when(repository.save(area)).thenReturn(area);

        Area areaSaved = service.save(area);
        service.deleteById(areaSaved.getId());

        Optional<Area> result = repository.findById(areaSaved.getId());
        assertFalse(result.isPresent());
        verify(repository, times(1)).deleteById(areaSaved.getId());
    }

    @Test
    void whenAreaAlreadyExists() {

    }

    @Test
    void whenAreaIsNull() {
        area  = null;
        Exception exception = assertThrows(AreaInvalidException.class, () -> service.save(area));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAreaNameIsInvalid() {
        area  = new Area(100L, "", "description_test", "Berlin", "color_test");
        Exception exception = assertThrows(AreaInvalidException.class, () -> service.save(area));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAreaDescriptionIsInvalid() {
        area  = new Area(100L, "name_area_test", "","Berlin", "color_test");
        Exception exception = assertThrows(AreaInvalidException.class, () -> service.save(area));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAreaColorIsInvalid() {
        area  = new Area(100L, "name_area_test", "description_test", "Berlin","");
        Exception exception = assertThrows(AreaInvalidException.class, () -> service.save(area));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAreaLocationIsInvalid() {
        area  = new Area(100L, "name_area_test", "description_test", "","color_test");
        Exception exception = assertThrows(AreaInvalidException.class, () -> service.save(area));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateAreaIdDoesNotExist() {
        area  = new Area(999L, "name_area_test", "description_test", "Berlin","color_test");
        Exception exception = assertThrows(AreaNotFoundException.class, () -> service.update(area));

        String expectedMessage = "Area not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteAreaIdDoesNotExist() {
        Exception exception = assertThrows(AreaNotFoundException.class, () -> service.deleteById(999L));

        String expectedMessage = "Area not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateIfIdIsInvalid() {
        area  = new Area(999L, "name_area_test", "description_test", "location_test","color_test");
        Exception exception = assertThrows(AreaNotFoundException.class, () -> service.update(area));

        String expectedMessage = "Area not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getRevenueById(){
//        Department department = new Department(300L, "Contact Manager Test 1", 4000, 14);
//        Department department2 = new Department(400L, "Contact Manager Test 2", 16000, 5);
//        area.setDepartments(List.of(department, department2));
//        service.save(area);
//
//        when(repository.getRevenueById(100L)).thenReturn(20000.0);
//        assertThat(service.getRevenueById(100L)).isEqualTo(20000.0);
    }

}