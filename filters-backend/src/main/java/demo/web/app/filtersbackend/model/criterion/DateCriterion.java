package demo.web.app.filtersbackend.model.criterion;

import demo.web.app.filtersbackend.model.Criterion;
import demo.web.app.filtersbackend.model.operator.DateComparisonOperator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class DateCriterion extends Criterion {
    @NotNull
    @Enumerated(EnumType.STRING)
    private DateComparisonOperator operator;
    @NotNull
    private Date value;

    public DateComparisonOperator getOperator() {
        return operator;
    }

    public void setOperator(DateComparisonOperator operator) {
        this.operator = operator;
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }
}
