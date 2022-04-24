package demo.web.app.filtersbackend.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class FilterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "filterEntity")
    private Set<CriteriaEntity> criteriaEntities;
    private String selection;

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

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
