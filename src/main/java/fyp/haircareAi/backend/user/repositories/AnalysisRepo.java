package fyp.haircareAi.backend.user.repositories;

import fyp.haircareAi.backend.user.entities.HairAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisRepo extends JpaRepository<HairAnalysisEntity,Long> {
//    List<HairAnalysisEntity> findByUserUserId(Long userId);
List<HairAnalysisEntity> findByUserId(long userId);
}
