package demo.web.app.filtersbackend.model;

import demo.web.app.filtersbackend.model.type.FilterSelection;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    @OneToMany(mappedBy = "filter")
    private List<Criterion> criteria;
    @NotNull
    @Enumerated(EnumType.STRING)
    private FilterSelection selection;

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

    public FilterSelection getSelection() {
        return selection;
    }

    public void setSelection(FilterSelection selection) {
        this.selection = selection;
    }

    public List<Criterion> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Criterion> criteria) {
        this.criteria = criteria;
    }
}
