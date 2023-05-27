package po.kinomorrigan.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import po.kinomorrigan.models.User;
import po.kinomorrigan.models.LoyaltyCard;
import po.kinomorrigan.models.repositories.UserRepository;
import po.kinomorrigan.models.repositories.LoyaltyCardRepository;

import java.util.Optional;


@Service
public class LoyaltyCardService
{   Logger logger = LoggerFactory.getLogger(LoyaltyCardService.class);
    private final UserRepository userRepository;
    private final LoyaltyCardRepository loyaltyCardRepository;

    public LoyaltyCardService(UserRepository userRepository,
                              LoyaltyCardRepository loyaltyCardRepository)
    {
        this.userRepository = userRepository;
        this.loyaltyCardRepository = loyaltyCardRepository;
    }

    public Optional<User> validateUser(String login)
    {
        logger.info("validujemy");
        Optional<User> reqUser  = Optional.ofNullable(userRepository.getUserByLogin(login));
        logger.info("zwalidowany");
        return reqUser;
    }
    public User getUserByLogin(String login)
    {
       User user  = userRepository.getUserByLogin(login);
        return user;
    }

    public boolean userHasLoyaltyCard(User user)
    {
        return user.getLoyaltyCard() != null;
    }
    public boolean createLoyaltyCard(String login)
    {
        User user = getUserByLogin(login);
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        logger.info(user.getLogin());
        user.setLoyaltyCard(loyaltyCard);
        saveLoyaltyCard(loyaltyCard,user);
        if(userHasLoyaltyCard(user))
            return true;
        else return false;
    }

    public void saveLoyaltyCard(LoyaltyCard loyaltyCard, User user)
    {
        loyaltyCardRepository.save(loyaltyCard);
        userRepository.save(user);
    }

}
