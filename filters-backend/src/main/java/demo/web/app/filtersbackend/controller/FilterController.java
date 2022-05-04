package demo.web.app.filtersbackend.controller;

import demo.web.app.filtersbackend.dto.FilterDto;
import demo.web.app.filtersbackend.model.Filter;
import demo.web.app.filtersbackend.service.FilterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FilterController {
    @Autowired
    FilterService filterService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value="/filters", method=RequestMethod.GET)
    public List<FilterDto> readFilters() {
        List<Filter> filters = filterService.getFilters();

        List<FilterDto> filterDtos = new ArrayList<>();
        for (Filter filter : filters) {
            filterDtos.add(modelMapper.map(filter, FilterDto.class));
        }

        return filterDtos;
    }

    @RequestMapping(value="/filters", method=RequestMethod.POST)
    public FilterDto createFilter(@RequestBody FilterDto filterDto) {
        Filter filterRequest = modelMapper.map(filterDto, Filter.class);
        Filter filter = filterService.createFilter(filterRequest);

        return modelMapper.map(filter, FilterDto.class);
    }
}
