package powercloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import powercloud.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT revenue FROM Department WHERE id = :id", nativeQuery = true)
    public double getRevenueByDepartment(@Param("id") Long id);

}
