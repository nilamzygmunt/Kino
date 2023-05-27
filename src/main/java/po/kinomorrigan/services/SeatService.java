package po.kinomorrigan.services;

import org.springframework.stereotype.Service;
import po.kinomorrigan.models.Seat;
import po.kinomorrigan.models.Hall;
import po.kinomorrigan.models.enums.Standard;
import po.kinomorrigan.models.repositories.HallRepository;
import po.kinomorrigan.models.repositories.SeatRepository;

import java.util.List;


@Service
public class SeatService {
    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository, HallRepository hallRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat getSeatById(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(
                () -> new IllegalArgumentException("seat not found")
        );
    }

    public List<Seat> addSeatsToHall(int newSeats, Hall hall)
    {
        List<Seat> seats = seatRepository.findAllByHall(hall);
        Standard standard = seats.get(0).getStandard();
        for(int i = 0; i < newSeats; i++)
        {
            Seat seat = new Seat(hall, standard);
            seats.add(seat);
            seatRepository.save(seat);
        }
        return seats;
    }

    public List<Seat> removeSeatsFromHall(int deleteSeats, Hall hall)
    {
        List<Seat> seats = seatRepository.findAllByHall(hall);
        for(int i = 0; i < deleteSeats; i++)
        {
            seats.get(0).removeFromHall();
            seats.remove(0);
        }
        return seats;
    }


}
