package fyp.haircareAi.backend.user.repositories;

import fyp.haircareAi.backend.user.entities.HairAnalysisEntity;
import fyp.haircareAi.backend.user.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepo extends JpaRepository<ReportEntity,Long> {

    List<ReportEntity> findByUserId(long userId);
}
