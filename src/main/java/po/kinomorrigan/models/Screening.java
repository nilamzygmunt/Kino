package po.kinomorrigan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime time;
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;
    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;
    @ManyToOne
    @JoinColumn(name = "repertoire_id", nullable = false)
    private Repertoire repertoire;

    public Screening(LocalDate date, LocalTime time, Film film, Hall hall, Repertoire repertoire) {
        this.date = date;
        this.time = time;
        this.film = film;
        this.hall = hall;
        this.repertoire = repertoire;
    }
}
