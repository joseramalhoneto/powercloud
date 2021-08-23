package powercloud.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import powercloud.exception.AreaInvalidException;
import powercloud.exception.AreaNotFoundException;
import powercloud.model.Area;
import powercloud.model.SubArea;
import powercloud.repository.AreaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AreaServiceTest {

    private Area area, area2;

    @Mock
    private AreaRepository repository;
    private AreaService service;

    @BeforeEach
    void setUp() {
        service = new AreaService(repository);
        area  = new Area(100L, "name_area_test", "description_test", "color_test");
        area2 = new Area(200L, "name_area_test", "description_test", "color_test");
    }

    @Test
    void save() {
        service.save(area);

        ArgumentCaptor<Area> argumentCaptor = ArgumentCaptor.forClass(Area.class);
        verify(repository).save(argumentCaptor.capture());
        Area result = argumentCaptor.getValue();

        assertThat(result).isEqualTo(area);
    }

    @Test
    void findById() {
        when(repository.findById(100L)).thenReturn(Optional.of(area));
        assertThat(service.findById(100L)).isEqualTo(area);
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
        area  = new Area(100L, "", "description_test", "color_test");
        Exception exception = assertThrows(AreaInvalidException.class, () -> service.save(area));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAreaDescriptionIsInvalid() {
        area  = new Area(100L, "name_area_test", "", "color_test");
        Exception exception = assertThrows(AreaInvalidException.class, () -> service.save(area));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAreaColorIsInvalid() {
        area  = new Area(100L, "name_area_test", "description_test", "");
        Exception exception = assertThrows(AreaInvalidException.class, () -> service.save(area));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateAreaIdDoesNotExist() {
        area  = new Area(999L, "name_area_test", "description_test", "color_test");
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
    void update() {
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
    void deleteById() {}

}