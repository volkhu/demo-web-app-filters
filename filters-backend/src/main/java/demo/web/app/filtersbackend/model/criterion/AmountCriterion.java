package demo.web.app.filtersbackend.model.criterion;

import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.operator.AmountComparisonOperator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
public class AmountCriterion extends Criterion {
    @NotNull
    @Enumerated(EnumType.STRING)
    private AmountComparisonOperator operator;
    @NotNull
    private Long value;

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
