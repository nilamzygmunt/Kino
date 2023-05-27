package po.kinomorrigan.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import po.kinomorrigan.models.LoyaltyCard;

@Repository

public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard, Long>
{
}
