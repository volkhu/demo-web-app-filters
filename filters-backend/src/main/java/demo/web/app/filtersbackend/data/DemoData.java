package demo.web.app.filtersbackend.data;

import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.Filter;
import demo.web.app.filtersbackend.repository.CriterionRepository;
import demo.web.app.filtersbackend.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DemoData {
    @Autowired
    FilterRepository filterRepository;

    @Autowired
    CriterionRepository criterionRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        // insert demo data after the spring app has started

        Criterion criterion1 = new Criterion();
        criterion1.setType("Amount");
        criterion1.setOperator("greater than");
        criterion1.setValue("5");

        Criterion criterion2 = new Criterion();
        criterion2.setType("Title");
        criterion2.setOperator("equals");
        criterion2.setValue("test");

        Criterion criterion3 = new Criterion();
        criterion3.setType("Date");
        criterion3.setOperator("until");
        criterion3.setValue("2022-04-15");

        List<Criterion> criteria1 = new ArrayList<Criterion>();
        criteria1.add(criterion1);
        criteria1.add(criterion2);

        List<Criterion> criteria2 = new ArrayList<Criterion>();
        criteria2.add(criterion3);

        Filter filter1 = new Filter();
        filter1.setId(1L);
        filter1.setName("My Filter 1");
        criterion1.setFilter(filter1);
        criterion2.setFilter(filter1);
        filter1.setCriteria(criteria1);
        filter1.setSelection("1");
        filterRepository.save(filter1);
        criterionRepository.save(criterion1);
        criterionRepository.save(criterion2);

        Filter filter2 = new Filter();
        filter2.setId(2L);
        filter2.setName("My Filter 2");
        criterion3.setFilter(filter2);
        filter1.setCriteria(criteria2);
        filter2.setSelection("2");
        filterRepository.save(filter2);
        criterionRepository.save(criterion3);
    }
}
