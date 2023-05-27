package po.kinomorrigan.models;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "reservation")
    private List<Ticket> tickets;
    @CreationTimestamp
    private LocalDate dateOfReservation;
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    public Reservation() {
        tickets = new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
