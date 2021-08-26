package powercloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import powercloud.model.Area;

import java.util.Map;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    @Query(value = "SELECT sum(revenue) FROM Department WHERE area_id = :id", nativeQuery = true)
    double getRevenueById(@Param("id") Long id);

    @Query(value = " SELECT * " +
                    " FROM ( " +
                        " SELECT area_id, sum(revenue) as sum_revenue FROM Department " +
                        " GROUP BY area_id) as tab_sum_revenue " +
                    " HAVING sum_revenue = max(sum_revenue) ", nativeQuery = true)
    Map<?, ?> getMaxRevenue();

}
