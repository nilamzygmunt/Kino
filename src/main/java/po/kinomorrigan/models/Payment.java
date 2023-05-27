package po.kinomorrigan.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import po.kinomorrigan.models.enums.PaymentType;

@Entity
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PaymentType paymentType;

    public Payment(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
