package powercloud.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import powercloud.exception.AreaInvalidException;
import powercloud.exception.AreaNotFoundException;
import powercloud.exception.SubAreaInvalidException;
import powercloud.exception.SubAreaNotFoundException;
import powercloud.model.Area;
import powercloud.model.SubArea;
import powercloud.repository.AreaRepository;
import powercloud.repository.SubAreaRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SubAreaServiceTest {

    private SubArea subArea1, subArea2;

    @Mock
    private SubAreaRepository repository;
    private SubAreaService service;

    @BeforeEach
    void setUp() {
        service = new SubAreaService(repository);
        subArea1 = new SubArea(100L, "name_subarea_test_1", 10000, 5 );
        subArea2 = new SubArea(200L, "name_subarea_test_2", 15000, 9 );
    }

    @Test
    void findById() {
        when(repository.findById(100L)).thenReturn(Optional.of(subArea1));
        assertThat(service.findById(100L)).isEqualTo(subArea1);
    }

    @Test
    void save() {
        service.save(subArea1);

        ArgumentCaptor<SubArea> argumentCaptor = ArgumentCaptor.forClass(SubArea.class);
        verify(repository).save(argumentCaptor.capture());
        SubArea result = argumentCaptor.getValue();

        assertThat(result).isEqualTo(subArea1);
    }

    @Test
    void update() {
    }

    @Test
    void saveAll() {
        service.saveAll(List.of(subArea1, subArea2));

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(argumentCaptor.capture());
        List result = argumentCaptor.getValue();

        assertThat(result).isEqualTo(List.of(subArea1, subArea2));
    }

    @Test
    void deleteAll() {
        service.saveAll(List.of(subArea1, subArea2));

        service.deleteAll();
        boolean exists = repository.existsById(100L);
        assertFalse(exists);

        exists = repository.existsById(200L);
        assertFalse(exists);
    }

    @Test
    void deleteById() {
    }

    @Test
    void whenSubAreaIsNull() {
        subArea1  = null;
        Exception exception = assertThrows(SubAreaInvalidException.class, () -> service.save(subArea1));

        String expectedMessage = "SubArea invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenSubAreaNameIsInvalid() {
        subArea1 = new SubArea(100L, "", 10000, 5 );
        Exception exception = assertThrows(SubAreaInvalidException.class, () -> service.save(subArea1));

        String expectedMessage = "SubArea invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenSubAreaEmployeesIsInvalid() {
        subArea1 = new SubArea(100L, "name_subarea_test_1", 10000, -99 );
        Exception exception = assertThrows(SubAreaInvalidException.class, () -> service.save(subArea1));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateSubAreaIdDoesNotExist() {
        subArea1 = new SubArea(999L, "name_subarea_test_1", 10000, 5 );
        Exception exception = assertThrows(SubAreaNotFoundException.class, () -> service.update(subArea1));

        String expectedMessage = "Area not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteSubAreaIdDoesNotExist() {
        Exception exception = assertThrows(SubAreaNotFoundException.class, () -> service.deleteById(999L));

        String expectedMessage = "Area not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}