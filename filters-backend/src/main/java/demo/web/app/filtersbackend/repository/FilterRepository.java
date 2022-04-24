package demo.web.app.filtersbackend.repository;

import demo.web.app.filtersbackend.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {

}
