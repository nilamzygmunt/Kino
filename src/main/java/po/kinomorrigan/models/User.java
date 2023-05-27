package po.kinomorrigan.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import po.kinomorrigan.models.enums.Role;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Column(unique=true)
    private String login;
    private String password;
    private Role role;
    @OneToOne
    private LoyaltyCard loyaltyCard;

    public User(String login, String name, String lastName)
    {
        this.login = login;
        this.firstName= name;
        this.lastName = lastName;
        loyaltyCard = null;
    }
    public String getLogin()
    {
        return login;
    }
    public LoyaltyCard getLoyaltyCard()
    {
        System.out.println(login+ " loyalty card: "+String.valueOf(loyaltyCard));
        return loyaltyCard;
    }
    public void setLoyaltyCard(LoyaltyCard loyaltyCard)
    {
       System.out.println("we set loyalty card as : " +String.valueOf(loyaltyCard));
        this.loyaltyCard = loyaltyCard;
    }
}
