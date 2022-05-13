package demo.web.app.filtersbackend.dto.converter;

import demo.web.app.filtersbackend.dto.CriterionDto;
import demo.web.app.filtersbackend.dto.criterion.AmountCriterionDto;
import demo.web.app.filtersbackend.dto.criterion.DateCriterionDto;
import demo.web.app.filtersbackend.dto.criterion.TitleCriterionDto;
import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.criterion.AmountCriterion;
import demo.web.app.filtersbackend.model.criterion.DateCriterion;
import demo.web.app.filtersbackend.model.criterion.TitleCriterion;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CriterionListConverter extends AbstractConverter<List<Criterion>, List<CriterionDto>> {

    private final ModelMapper modelMapper;

    public CriterionListConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected List<CriterionDto> convert(List<Criterion> source) {
        List<CriterionDto> destination = new ArrayList<CriterionDto>();

        if (modelMapper != null && source != null) {
            for (Criterion criterion : source) {
                if (criterion instanceof AmountCriterion) {
                    destination.add(modelMapper.map(criterion, AmountCriterionDto.class));
                } else if (criterion instanceof TitleCriterion) {
                    destination.add(modelMapper.map(criterion, TitleCriterionDto.class));
                } else if (criterion instanceof DateCriterion) {
                    destination.add(modelMapper.map(criterion, DateCriterionDto.class));
                }
            }
        }

        return destination;
    }
}
