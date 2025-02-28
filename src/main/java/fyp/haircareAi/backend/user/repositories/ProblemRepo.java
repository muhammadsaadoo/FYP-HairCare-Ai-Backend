package fyp.haircareAi.backend.user.repositories;

import fyp.haircareAi.backend.user.entities.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepo extends JpaRepository<ProblemEntity,Integer> {
}
