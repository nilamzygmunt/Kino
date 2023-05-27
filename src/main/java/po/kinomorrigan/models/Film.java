package po.kinomorrigan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate premiere;
    private int durationTimeMinutes;
    private String description;
    private String ageCategory;
    @ManyToOne
    @JoinColumn(name="genre_id", nullable=false)
    private FilmGenre genre;

    public Film(String title, LocalDate premiere, int durationTimeMinutes, String description, String ageCategory, FilmGenre genre) {
        this.title = title;
        this.premiere = premiere;
        this.durationTimeMinutes = durationTimeMinutes;
        this.description = description;
        this.ageCategory = ageCategory;
        this.genre = genre;
    }

    public Film(Long id, String title, LocalDate premiere, int durationTimeMinutes, String description, String ageCategory, FilmGenre genre) {
        this.id = id;
        this.title = title;
        this.premiere = premiere;
        this.durationTimeMinutes = durationTimeMinutes;
        this.description = description;
        this.ageCategory = ageCategory;
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
