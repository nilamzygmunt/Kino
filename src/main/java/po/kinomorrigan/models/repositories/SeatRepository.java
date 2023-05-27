package po.kinomorrigan.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import po.kinomorrigan.models.Hall;
import po.kinomorrigan.models.Seat;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByHall(Hall hall);
}
