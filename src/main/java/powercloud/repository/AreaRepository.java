package powercloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import powercloud.model.Area;
import powercloud.model.Department;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    @Query(value = "SELECT sum(revenue) FROM Department WHERE area_id = :id", nativeQuery = true)
    double getRevenueById(@Param("id") Long id);

    @Query(value = "SELECT a.id, a.color, a.description, a.name " +
            "FROM Area a, Department d" +
            "WHERE area_id = :id",
            nativeQuery = true)
    public Area getMaxRevenue();


}
