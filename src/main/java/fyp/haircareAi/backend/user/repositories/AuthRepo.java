package fyp.haircareAi.backend.user.repositories;


import fyp.haircareAi.backend.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthRepo extends JpaRepository<UserEntity,Integer> {
    UserEntity findByEmail(String email);

}
