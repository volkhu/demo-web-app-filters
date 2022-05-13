package demo.web.app.filtersbackend.model.criterion;

import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.operator.TitleComparisonOperator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class TitleCriterion extends Criterion {
    @NotNull
    @Enumerated(EnumType.STRING)
    private TitleComparisonOperator operator;
    @NotEmpty
    private String value;

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
