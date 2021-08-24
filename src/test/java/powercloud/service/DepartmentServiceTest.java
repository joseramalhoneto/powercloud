package powercloud.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import powercloud.exception.DepartmentException;
import powercloud.exception.DepartmentNotFoundException;
import powercloud.model.Department;
import powercloud.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DepartmentServiceTest {

    private Department department1, department2;

    @Mock
    private DepartmentRepository repository;
    private DepartmentService service;

    @BeforeEach
    void setUp() {
        service = new DepartmentService(repository);
        department1 = new Department(100L, "name_subarea_test_1", 10000, 5 );
        department2 = new Department(200L, "name_subarea_test_2", 15000, 9 );
    }

    @Test
    void findById() {
        when(repository.findById(100L)).thenReturn(Optional.of(department1));
        assertThat(service.findById(100L)).isEqualTo(department1);
    }

    @Test
    void save() {
        service.save(department1);

        ArgumentCaptor<Department> argumentCaptor = ArgumentCaptor.forClass(Department.class);
        verify(repository).save(argumentCaptor.capture());
        Department result = argumentCaptor.getValue();

        assertThat(result).isEqualTo(department1);
    }

    @Test
    void update() {
    }

    @Test
    void saveAll() {
        service.saveAll(List.of(department1, department2));

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(argumentCaptor.capture());
        List result = argumentCaptor.getValue();

        assertThat(result).isEqualTo(List.of(department1, department2));
    }

    @Test
    void deleteAll() {
        service.saveAll(List.of(department1, department2));

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
        department1 = null;
        Exception exception = assertThrows(DepartmentException.class, () -> service.save(department1));

        String expectedMessage = "SubArea invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenSubAreaNameIsInvalid() {
        department1 = new Department(100L, "", 10000, 5 );
        Exception exception = assertThrows(DepartmentException.class, () -> service.save(department1));

        String expectedMessage = "SubArea invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenSubAreaEmployeesIsInvalid() {
        department1 = new Department(100L, "name_subarea_test_1", 10000, -99 );
        Exception exception = assertThrows(DepartmentException.class, () -> service.save(department1));

        String expectedMessage = "Area invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateSubAreaIdDoesNotExist() {
        department1 = new Department(999L, "name_subarea_test_1", 10000, 5 );
        Exception exception = assertThrows(DepartmentNotFoundException.class, () -> service.update(department1));

        String expectedMessage = "Area not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteSubAreaIdDoesNotExist() {
        Exception exception = assertThrows(DepartmentNotFoundException.class, () -> service.deleteById(999L));

        String expectedMessage = "Area not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}