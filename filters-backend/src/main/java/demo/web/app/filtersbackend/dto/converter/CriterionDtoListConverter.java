package demo.web.app.filtersbackend.dto.converter;

import demo.web.app.filtersbackend.dto.CriterionDto;
import demo.web.app.filtersbackend.dto.type.CriterionType;
import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.criterion.AmountCriterion;
import demo.web.app.filtersbackend.model.criterion.DateCriterion;
import demo.web.app.filtersbackend.model.criterion.TitleCriterion;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CriterionDtoListConverter extends AbstractConverter<List<CriterionDto>, List<Criterion>> {

    private final ModelMapper modelMapper;

    public CriterionDtoListConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected List<Criterion> convert(List<CriterionDto> source) {
        List<Criterion> destination = new ArrayList<Criterion>();

        if (modelMapper != null && source != null) {
            for (CriterionDto criterionDto : source) {
                if (criterionDto.getType() == CriterionType.AMOUNT) {
                    destination.add(modelMapper.map(criterionDto, AmountCriterion.class));
                } else if (criterionDto.getType() == CriterionType.TITLE) {
                    destination.add(modelMapper.map(criterionDto, TitleCriterion.class));
                } else if (criterionDto.getType() == CriterionType.DATE) {
                    destination.add(modelMapper.map(criterionDto, DateCriterion.class));
                }
            }
        }

        return destination;
    }
}
