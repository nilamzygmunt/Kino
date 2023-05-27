package po.kinomorrigan.services;

import org.springframework.stereotype.Service;
import po.kinomorrigan.models.Hall;
import po.kinomorrigan.models.repositories.HallRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HallService
{
    private final HallRepository hallRepository;
    private final SeatService seatService;

    public HallService(HallRepository hallRepository, SeatService seatService)
    {
        this.hallRepository = hallRepository;
        this.seatService = seatService;
    }

    public List<Hall> getHalls()
    {
        List<Hall> halls = hallRepository.findAll();
        return halls;
    }

    public Hall getHallById(Long hallId) throws IllegalArgumentException
    {
        Hall hall = hallRepository.findById(hallId).orElseThrow(
                () -> new IllegalArgumentException("hall not found")
        );
        return hall;
    }

    public Optional<Hall> validateNumberOfSeats(Long hallId, Integer newSeatsNumber)
    {
        Hall hall = getHallById(hallId);
        if(newSeatsNumber < 10 || newSeatsNumber > 50)
        {
            return Optional.empty();
        }
        addOrRemoveSets(hall, newSeatsNumber);
        return Optional.of(hall);
    }

    private void addOrRemoveSets(Hall hall, int newSeatsNumber)
    {
        if(hall.getNumberOfSeats() > newSeatsNumber)
            hall.updateSeats(seatService.removeSeatsFromHall( hall.getNumberOfSeats()- newSeatsNumber, hall));
        else
            hall.updateSeats(seatService.addSeatsToHall(newSeatsNumber - hall.getNumberOfSeats(), hall));
        hall.setNumberOfSeats(newSeatsNumber);
        saveHall(hall);
    }

    public void saveHall(Hall hall)
    {
        hallRepository.save(hall);
    }
}
