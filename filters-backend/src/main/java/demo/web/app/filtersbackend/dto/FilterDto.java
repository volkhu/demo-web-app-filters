package demo.web.app.filtersbackend.dto;

import demo.web.app.filtersbackend.model.type.MatchType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class FilterDto {
    private Long id;
    @NotEmpty
    @Size(max = 64)
    private String name;
    @NotNull
    private MatchType matchType;
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

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public List<CriterionDto> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<CriterionDto> criteria) {
        this.criteria = criteria;
    }
}
