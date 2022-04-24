package demo.web.app.filtersbackend.service;

import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.Filter;
import demo.web.app.filtersbackend.repository.CriterionRepository;
import demo.web.app.filtersbackend.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService {
    @Autowired
    FilterRepository filterRepository;

    @Autowired
    CriterionRepository criterionRepository;

    public Filter createFilter(Filter filter) {
        Filter savedFilter = filterRepository.save(filter);

        for (Criterion criterion : filter.getCriteria()) {
            criterion.setFilter(filter);
            criterionRepository.save(criterion);
        }

        return savedFilter;
    }

    public List<Filter> getFilters() {
        return filterRepository.findAll();
    }
}
