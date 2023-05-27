package po.kinomorrigan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numberOfSeats;
    @OneToMany(mappedBy = "hall")
    private List<Seat> seats = new ArrayList<>();

    public Hall(int numberOfSeats)
    {
        this.numberOfSeats = numberOfSeats;
    }
    public int getNumberOfSeats()
    {
        return numberOfSeats;
    }
    public Long getHallId()
    {
        return id;
    }
    public void setNumberOfSeats(int newSeatsNumber)
    {
        numberOfSeats = newSeatsNumber;
    }

    public void updateSeats(List<Seat> newSeats)
    {
        seats = newSeats;
    }
}
