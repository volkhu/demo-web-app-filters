package demo.web.app.filtersbackend;

import demo.web.app.filtersbackend.config.ModelMapperConfig;
import demo.web.app.filtersbackend.dto.CriterionDto;
import demo.web.app.filtersbackend.dto.FilterDto;
import demo.web.app.filtersbackend.dto.criterion.AmountCriterionDto;
import demo.web.app.filtersbackend.dto.criterion.DateCriterionDto;
import demo.web.app.filtersbackend.dto.type.CriterionType;
import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.Filter;
import demo.web.app.filtersbackend.model.criterion.AmountCriterion;
import demo.web.app.filtersbackend.model.criterion.DateCriterion;
import demo.web.app.filtersbackend.model.type.MatchType;
import demo.web.app.filtersbackend.model.type.operator.AmountComparisonOperator;
import demo.web.app.filtersbackend.model.type.operator.DateComparisonOperator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelMapperTests {

    private static ModelMapper modelMapper;

    @BeforeAll
    static void setUp() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        modelMapper = modelMapperConfig.modelMapper();
    }

    @Test
    void test_FilterEntityToDtoConversion_SimpleFields() {
        // create a filter entity
        Filter filter = new Filter();
        filter.setId(7L);
        filter.setName("Test Filter");
        filter.setMatchType(MatchType.ANY);

        // convert to a filter DTO and make sure fields kept their values
        FilterDto filterDto = modelMapper.map(filter, FilterDto.class);
        assertEquals(7L, filterDto.getId());
        assertEquals("Test Filter", filterDto.getName());
        assertEquals(MatchType.ANY, filterDto.getMatchType());
    }

    @Test
    void test_FilterDtoToEntityConversion_SimpleFields() {
        // create a filter DTO
        FilterDto filterDto = new FilterDto();
        filterDto.setId(134L);
        filterDto.setName("Test Filter DTO");
        filterDto.setMatchType(MatchType.NONE);

        // convert to a filter entity and make sure fields kept their values
        Filter filter = modelMapper.map(filterDto, Filter.class);
        assertEquals(134L, filter.getId());
        assertEquals("Test Filter DTO", filter.getName());
        assertEquals(MatchType.NONE, filter.getMatchType());
    }

    @Test
    void test_FilterCriteriaEntityToDtoConversion() {
        // create a filter entity
        Filter filter = new Filter();
        List<Criterion> criteria = new ArrayList<>();

        // add an amount criterion
        AmountCriterion amountCriterion = new AmountCriterion();
        amountCriterion.setId(67L);
        amountCriterion.setOperator(AmountComparisonOperator.GREATER_THAN);
        amountCriterion.setValue(850L);
        criteria.add(amountCriterion);
        filter.setCriteria(criteria);

        // convert the filter (along with the criterion) to a DTO
        FilterDto filterDto = modelMapper.map(filter, FilterDto.class);
        assertEquals(1, filterDto.getCriteria().size());

        // check that the type and the properties of the criterion were retained
        AmountCriterionDto amountCriterionDto = (AmountCriterionDto) filterDto.getCriteria().get(0);
        assertEquals(67L, amountCriterionDto.getId());
        assertEquals(CriterionType.AMOUNT, amountCriterionDto.getType());
        assertEquals(AmountComparisonOperator.GREATER_THAN, amountCriterionDto.getOperator());
        assertEquals(850L, amountCriterionDto.getValue());
    }

    @Test
    void test_FilterCriteriaDtoToEntityConversion() throws ParseException {
        // create filter DTO
        FilterDto filterDto = new FilterDto();
        List<CriterionDto> criteriaDto = new ArrayList<>();

        // add a date criterion DTO
        Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-14");
        DateCriterionDto dateCriterionDto = new DateCriterionDto();
        dateCriterionDto.setId(99L);
        dateCriterionDto.setOperator(DateComparisonOperator.UNTIL);
        dateCriterionDto.setValue(testDate);
        criteriaDto.add(dateCriterionDto);
        filterDto.setCriteria(criteriaDto);

        // convert the filter (along with the criterion) to an entity
        Filter filter = modelMapper.map(filterDto, Filter.class);
        assertEquals(1, filter.getCriteria().size());

        // check that the type and the properties of the criterion were retained
        DateCriterion dateCriterion = (DateCriterion) filter.getCriteria().get(0);
        assertEquals(99L, dateCriterion.getId());
        assertEquals(DateComparisonOperator.UNTIL, dateCriterion.getOperator());
        assertEquals(testDate, dateCriterion.getValue());
    }
}
