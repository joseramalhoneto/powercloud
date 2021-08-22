package powercloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import powercloud.model.SubArea;

@Repository
public interface SubAreaRepository extends JpaRepository<SubArea, Long> {
}
