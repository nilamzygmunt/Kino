package po.kinomorrigan.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import po.kinomorrigan.models.TicketType;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
}
