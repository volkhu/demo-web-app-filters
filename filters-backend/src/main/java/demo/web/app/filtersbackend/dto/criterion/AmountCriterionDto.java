package demo.web.app.filtersbackend.dto.criterion;

import demo.web.app.filtersbackend.dto.CriterionDto;
import demo.web.app.filtersbackend.dto.type.CriterionType;
import demo.web.app.filtersbackend.model.type.operator.AmountComparisonOperator;

import javax.validation.constraints.NotNull;

public class AmountCriterionDto extends CriterionDto {
    @NotNull
    private AmountComparisonOperator operator;
    @NotNull
    private Long value;

    public AmountCriterionDto() {
        this.setType(CriterionType.AMOUNT);
    }

    public AmountComparisonOperator getOperator() {
        return operator;
    }

    public void setOperator(AmountComparisonOperator operator) {
        this.operator = operator;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
