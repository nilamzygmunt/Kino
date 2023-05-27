package po.kinomorrigan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import po.kinomorrigan.models.enums.Standard;

@Entity
@NoArgsConstructor
@Getter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
    private Standard standard;

    public Seat(Hall hall, Standard standard) {
        this.hall = hall;
        this.standard = standard;
    }

    public void removeFromHall()
    {
        this.hall = null;
    }
}

