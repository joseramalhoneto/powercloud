package powercloud.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import powercloud.exception.DepartmentInvalidException;
import powercloud.exception.DepartmentNotFoundException;
import powercloud.model.Department;
import powercloud.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DepartmentServiceTest {

    private Department department1, department2;

    @InjectMocks
    private DepartmentService service;

    @Mock
    private DepartmentRepository repository;

    @BeforeEach
    void setUp() {
        service = new DepartmentService(repository);
        department1 = new Department(100L, "name_department_test_1", 10000, 5 );
        department2 = new Department(200L, "name_department_test_2", 15000, 9 );
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(department1, department2));
        List<Department> result = service.findAll();

        assertThat(result).isEqualTo(List.of(department1, department2));
        assertEquals(2, result.size());
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
        department1 = new Department(100L, "name_department_test_X", 5000, 9 );

        when(repository.existsById(any())).thenReturn(true);
        when(repository.save(department1)).thenReturn(department1);
        Department departmentUpdated = service.update(department1);

        assertEquals(100L, departmentUpdated.getId());
        assertEquals("name_department_test_X", departmentUpdated.getName());
        assertEquals(5000, departmentUpdated.getRevenue());
        assertEquals(9, departmentUpdated.getEmployees());
        verify(repository, times(1)).save(department1);
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
        when(repository.existsById(any())).thenReturn(true);
        when(repository.save(department1)).thenReturn(department1);

        Department departmentSaved = service.save(department1);
        service.deleteById(departmentSaved.getId());

        Optional<Department> result = repository.findById(departmentSaved.getId());
        assertFalse(result.isPresent());
        verify(repository, times(1)).deleteById(departmentSaved.getId());
    }

    @Test
    void whenDepartmentIsNull() {
        department1 = null;
        Exception exception = assertThrows(DepartmentInvalidException.class, () -> service.save(department1));

        String expectedMessage = "Department invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenDepartmentNameIsInvalid() {
        department1 = new Department(100L, "", 10000, 5 );
        Exception exception = assertThrows(DepartmentInvalidException.class, () -> service.save(department1));

        String expectedMessage = "Department invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenDepartmentEmployeesIsInvalid() {
        department1 = new Department(100L, "name_department_test_1", 10000, -99 );
        Exception exception = assertThrows(DepartmentInvalidException.class, () -> service.save(department1));

        String expectedMessage = "Department invalid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateDepartmentIdDoesNotExist() {
        department1 = new Department(999L, "name_department_test_1", 10000, 5 );
        Exception exception = assertThrows(DepartmentNotFoundException.class, () -> service.update(department1));

        String expectedMessage = "Department not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteDepartmentIdDoesNotExist() {
        Exception exception = assertThrows(DepartmentNotFoundException.class, () -> service.deleteById(999L));

        String expectedMessage = "Department not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getRevenueByDepartment() {
        when(repository.save(department1)).thenReturn(department1);
        when(service.getRevenueByDepartment(department1.getId())).thenReturn(10000.0);
        Department departmentSaved = service.save(department1);

        double revenue = service.getRevenueByDepartment(departmentSaved.getId());

        assertEquals(revenue, departmentSaved.getRevenue());
        verify(repository, times(1)).getRevenueByDepartment(departmentSaved.getId());
    }

}