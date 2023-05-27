package po.kinomorrigan.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import po.kinomorrigan.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
