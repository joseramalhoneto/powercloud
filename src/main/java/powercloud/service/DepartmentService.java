package powercloud.service;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import powercloud.exception.DepartmentException;
import powercloud.exception.DepartmentNotFoundException;
import powercloud.model.Department;
import powercloud.repository.DepartmentRepository;
import powercloud.utility.DepartmentUtility;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    @Autowired
    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<Department> findAll() {
        return repository.findAll();
    }

    public Department findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("SubArea not found."));
    }

    @Synchronized
    public Department save(Department department) {
        if(DepartmentUtility.isSubAreaValid(department) == false)
            throw new DepartmentException("SubArea invalid.");

        return repository.save(department);
    }

    @Synchronized
    public Department update(Department department) {
        if(repository.existsById(department.getId()) == false)
            throw new DepartmentNotFoundException("SubArea not found.");

        return repository.save(department);
    }

    public void saveAll(List<Department> areas) {
        repository.saveAll(areas);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    @Synchronized
    public void deleteById(Long id) {
        if(repository.existsById(id) == false)
            throw new DepartmentNotFoundException("SubArea not found.");

        repository.deleteById(id);
    }

    public Department getMaxRevenue() {
        List<Department> departments = repository.findAll();
        Optional<Department> subArea = departments
                                        .stream()
                                        .max(Comparator.comparing(Department::getRevenue));
        return subArea.get();
    }

    public Department getMinRevenue() {
        List<Department> departments = repository.findAll();
        Optional<Department> subArea = departments
                                        .stream()
                                        .min(Comparator.comparing(Department::getRevenue));
        return subArea.get();
    }

}
