package po.kinomorrigan.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
public class Repertoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate validFrom;
    private LocalDate validTo;

    public List<Screening> getScreenings() {
        return screenings;
    }

    @OneToMany(mappedBy = "repertoire")
    private List<Screening> screenings;
}
