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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FilterController {
    @Autowired
    FilterService filterService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/filters", method = RequestMethod.GET)
    public List<FilterDto> readFilters() {
        List<Filter> filters = filterService.getFilters();

        return filters.stream().map(filter -> modelMapper.map(filter, FilterDto.class)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/filters", method = RequestMethod.POST)
    public FilterDto createFilter(@Valid @RequestBody FilterDto filterDto) {
        Filter filterRequest = modelMapper.map(filterDto, Filter.class);
        Filter filter = filterService.createFilter(filterRequest);

        return modelMapper.map(filter, FilterDto.class);
    }
}
