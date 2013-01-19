package machinesimulator.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * User: janus
 * Date: 13-01-12
 * Time: 17:56
 */
@Entity
@Table(name = "Machines")
public class Machine {
    @Id
    @GeneratedValue
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private String name;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date onTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date offTime;

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

    public Date getOnTime() {
        return onTime;
    }

    public void setOnTime(Date onTime) {
        this.onTime = onTime;
    }

    public Date getOffTime() {
        return offTime;
    }

    public void setOffTime(Date offTime) {
        this.offTime = offTime;
    }
}
