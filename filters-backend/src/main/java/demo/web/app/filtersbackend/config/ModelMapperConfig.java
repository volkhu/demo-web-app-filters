package demo.web.app.filtersbackend.config;

import demo.web.app.filtersbackend.dto.CriterionDto;
import demo.web.app.filtersbackend.dto.FilterDto;
import demo.web.app.filtersbackend.dto.converter.CriterionDtoListConverter;
import demo.web.app.filtersbackend.dto.converter.CriterionListConverter;
import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.Filter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;

import java.util.List;

@org.springframework.context.annotation.Configuration // avoid model mapper and spring name conflicts
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Convert the list of criteria entities in a filter entity to a list of criteria DTOs in a filter DTO using
        // a custom converter to preserve the properties of various possible criterion subclasses
        Converter<List<Criterion>, List<CriterionDto>> criterionListConverter = new CriterionListConverter(modelMapper);
        TypeMap<Filter, FilterDto> typeMap = modelMapper.createTypeMap(Filter.class, FilterDto.class);
        typeMap.addMappings(mapper -> mapper.using(criterionListConverter)
                .map(Filter::getCriteria, FilterDto::setCriteria));

        // And also for the other way around
        Converter<List<CriterionDto>, List<Criterion>> criterionDtoListConverter = new CriterionDtoListConverter(modelMapper);
        TypeMap<FilterDto, Filter> typeMap2 = modelMapper.createTypeMap(FilterDto.class, Filter.class);
        typeMap2.addMappings(mapper -> mapper.using(criterionDtoListConverter)
                .map(FilterDto::getCriteria, Filter::setCriteria));

        return modelMapper;
    }
}
