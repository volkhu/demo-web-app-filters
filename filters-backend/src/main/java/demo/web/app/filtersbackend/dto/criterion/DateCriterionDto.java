package demo.web.app.filtersbackend.dto.criterion;

import com.fasterxml.jackson.annotation.JsonFormat;
import demo.web.app.filtersbackend.dto.CriterionDto;
import demo.web.app.filtersbackend.dto.type.CriterionType;
import demo.web.app.filtersbackend.model.operator.DateComparisonOperator;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DateCriterionDto extends CriterionDto {
    @NotNull
    private DateComparisonOperator operator;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date value;

    public DateCriterionDto() {
        this.setType(CriterionType.DATE);
    }

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
