package fyp.haircareAi.backend.user.repositories;


import fyp.haircareAi.backend.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AuthRepo extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE u.role = :role")
    List<UserEntity> findByRole(@Param("role") UserEntity.Role role);


}
