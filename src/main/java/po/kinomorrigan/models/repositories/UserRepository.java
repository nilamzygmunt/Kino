package po.kinomorrigan.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import po.kinomorrigan.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User getUserByLogin(String login);
}
