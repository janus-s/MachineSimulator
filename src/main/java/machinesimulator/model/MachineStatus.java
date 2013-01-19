package machinesimulator.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * User: janus
 * Date: 13-01-12
 * Time: 19:18
 */
@Entity
@Table(name = "MachineStatuses")
public class MachineStatus {
    @Id
    @GeneratedValue
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private boolean status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
