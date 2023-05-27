package po.kinomorrigan.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import po.kinomorrigan.models.Repertoire;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {
}
