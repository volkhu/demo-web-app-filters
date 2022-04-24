package demo.web.app.filtersbackend.repository;

import demo.web.app.filtersbackend.model.Criterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriterionRepository extends JpaRepository<Criterion, Long> {

}
