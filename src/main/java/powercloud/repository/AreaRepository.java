package powercloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import powercloud.model.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
}
