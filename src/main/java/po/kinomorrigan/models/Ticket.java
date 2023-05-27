package po.kinomorrigan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TicketType type;
    private double price;
    private boolean isDiscount;
    private boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;
    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Ticket(TicketType type, double price, boolean isDiscount, boolean isPaid, Screening screening, Seat seat, Reservation reservation) {
        this.type = type;
        this.price = price;
        this.isDiscount = isDiscount;
        this.isPaid = isPaid;
        this.screening = screening;
        this.seat = seat;
        this.reservation = reservation;
    }

    public Ticket(Screening screening, Seat seat, Reservation reservation) {
        this.screening = screening;
        this.seat = seat;
        this.reservation = reservation;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
