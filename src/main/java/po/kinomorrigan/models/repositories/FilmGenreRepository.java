package po.kinomorrigan.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import po.kinomorrigan.models.FilmGenre;

@Repository
public interface FilmGenreRepository extends JpaRepository<FilmGenre, Long> {
}
