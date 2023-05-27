package po.kinomorrigan.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import po.kinomorrigan.models.Hall;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
}
