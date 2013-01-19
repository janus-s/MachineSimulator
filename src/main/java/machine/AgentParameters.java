package machine;

import agents.MonitoringParams;

import java.io.Serializable;

/**
 * User: janus
 * Date: 12-10-17
 * Time: 13:27
 */
public class AgentParameters implements Serializable {
    private String machineName;
    private String agentName;
    private String paramName;
    private String comment;

    public AgentParameters(String machineName, MonitoringParams params) {
        this.machineName = machineName;
        this.agentName = params.getAgent();
        this.paramName = params.getParam();
        this.comment = params.getComment();
    }

    public String getMachineName() {
        return machineName;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getParamName() {
        return paramName;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentParameters that = (AgentParameters) o;

        if (agentName != null ? !agentName.equals(that.agentName) : that.agentName != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (machineName != null ? !machineName.equals(that.machineName) : that.machineName != null) return false;
        if (paramName != null ? !paramName.equals(that.paramName) : that.paramName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = machineName != null ? machineName.hashCode() : 0;
        result = 31 * result + (agentName != null ? agentName.hashCode() : 0);
        result = 31 * result + (paramName != null ? paramName.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
