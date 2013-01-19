package machinesimulator.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * User: janus
 * Date: 13-01-12
 * Time: 17:54
 */
@Entity
@Table(name = "Parameters")
public class Parameter {
    @Id
    @GeneratedValue
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private float value;
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerTime;
    @ManyToOne
    private Machine machine;
    @ManyToOne
    private ParameterDict parameterDict;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public ParameterDict getParameterDict() {
        return parameterDict;
    }

    public void setParameterDict(ParameterDict parameterDict) {
        this.parameterDict = parameterDict;
    }
}
