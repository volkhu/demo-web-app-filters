package demo.web.app.filtersbackend.controller;

import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.Filter;
import demo.web.app.filtersbackend.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FilterController {
    @Autowired
    FilterService filterService;

    @RequestMapping(value="/filters", method=RequestMethod.GET)
    public List<Filter> readFilters() {
        return filterService.getFilters();
    }

    @RequestMapping(value="/filters", method=RequestMethod.POST)
    public Filter createFilter(@RequestBody Filter filter) {
        return filterService.createFilter(filter);
    }
}
