package demo.web.app.filtersbackend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import demo.web.app.filtersbackend.dto.criterion.AmountCriterionDto;
import demo.web.app.filtersbackend.dto.criterion.DateCriterionDto;
import demo.web.app.filtersbackend.dto.criterion.TitleCriterionDto;
import demo.web.app.filtersbackend.dto.type.CriterionType;

import javax.validation.constraints.NotNull;

// Map subclasses to the type property for the deserializer
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AmountCriterionDto.class, name = "AMOUNT"),
    @JsonSubTypes.Type(value = TitleCriterionDto.class, name = "TITLE"),
    @JsonSubTypes.Type(value = DateCriterionDto.class, name = "DATE")
})
public class CriterionDto {
    private Long id;
    @NotNull
    private CriterionType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CriterionType getType() {
        return type;
    }

    public void setType(CriterionType type) {
        this.type = type;
    }
}
