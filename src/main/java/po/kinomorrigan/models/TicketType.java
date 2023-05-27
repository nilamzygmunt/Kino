package po.kinomorrigan.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double basePrice;
    private double discount;

    public TicketType(String name, String description, double basePrice, double discount) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.discount = discount;
    }

    public String getBaseTicketPrice() {
        return String.format("%.2f", basePrice);
    }

    public String getReducedTicketPrice() {
        return String.format("%.2f", basePrice - basePrice * discount);
    }
}
