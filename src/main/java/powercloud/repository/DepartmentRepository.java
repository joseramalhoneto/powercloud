package powercloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import powercloud.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
