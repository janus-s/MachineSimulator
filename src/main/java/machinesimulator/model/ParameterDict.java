package machinesimulator.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: janus
 * Date: 13-01-12
 * Time: 17:12
 */
@Entity
@Table(name = "ParametersDict")
public class ParameterDict {
    @Id
    @GeneratedValue
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private String name;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
