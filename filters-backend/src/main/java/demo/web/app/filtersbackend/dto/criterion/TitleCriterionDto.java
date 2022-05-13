package demo.web.app.filtersbackend.dto.criterion;

import demo.web.app.filtersbackend.dto.CriterionDto;
import demo.web.app.filtersbackend.dto.type.CriterionType;
import demo.web.app.filtersbackend.model.type.operator.TitleComparisonOperator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TitleCriterionDto extends CriterionDto {
    @NotNull
    private TitleComparisonOperator operator;
    @NotEmpty
    private String value;

    public TitleCriterionDto() {
        this.setType(CriterionType.TITLE);
    }

    public TitleComparisonOperator getOperator() {
        return operator;
    }

    public void setOperator(TitleComparisonOperator operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
