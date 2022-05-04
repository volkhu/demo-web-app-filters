package demo.web.app.filtersbackend.dto;

import demo.web.app.filtersbackend.model.type.Selection;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class FilterDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private Selection selection;
    @NotEmpty
    private List<@Valid CriterionDto> criteria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

    public List<CriterionDto> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<CriterionDto> criteria) {
        this.criteria = criteria;
    }
}
