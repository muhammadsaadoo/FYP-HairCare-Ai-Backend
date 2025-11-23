package fyp.haircareAi.backend.user.repositories;

import fyp.haircareAi.backend.user.entities.HairAnalysisEntity;
import fyp.haircareAi.backend.user.entities.UserPrimaryDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPrimaryDetailsRepository extends JpaRepository<UserPrimaryDetailsEntity,Long> {
//    List<HairAnalysisEntity> findByUserUserId(Long userId);
List<UserPrimaryDetailsRepository> findByUserId(long userId);
}
