package po.kinomorrigan.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import po.kinomorrigan.models.Screening;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}
