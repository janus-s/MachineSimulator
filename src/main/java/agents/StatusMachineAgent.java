package agents;

import java.io.Serializable;

/**
 * User: janus
 * Date: 12-10-23
 * Time: 19:07
 */
public class StatusMachineAgent implements Serializable {
    private boolean isOnMachine;
    private boolean isStartMachine;
    private boolean isTemperatureMonitoring;
    private boolean isMachineTimeMonitoring;
    private boolean isMachineOperationTimeMonitoring;
    private boolean isCoolantLevelMonitoring;
    private boolean isCuttingForceMonitoring;
    private boolean isVibrationMonitoring;

    public StatusMachineAgent() {

    }

    public StatusMachineAgent(boolean isOnMachine,
                              boolean isStartMachine,
                              boolean isTemperatureMonitoring,
                              boolean isMachineTimeMonitoring,
                              boolean isMachineOperationTimeMonitoring,
                              boolean isCoolantLevelMonitoring,
                              boolean isCuttingForceMonitoring,
                              boolean isVibrationMonitoring) {
        this.isOnMachine = isOnMachine;
        this.isStartMachine = isStartMachine;
        this.isTemperatureMonitoring = isTemperatureMonitoring;
        this.isMachineTimeMonitoring = isMachineTimeMonitoring;
        this.isMachineOperationTimeMonitoring = isMachineOperationTimeMonitoring;
        this.isCoolantLevelMonitoring = isCoolantLevelMonitoring;
        this.isCuttingForceMonitoring = isCuttingForceMonitoring;
        this.isVibrationMonitoring = isVibrationMonitoring;
    }

    public boolean isOnMachine() {
        return isOnMachine;
    }

    public boolean isStartMachine() {
        return isStartMachine;
    }

    public boolean isTemperatureMonitoring() {
        return isTemperatureMonitoring;
    }

    public boolean isMachineTimeMonitoring() {
        return isMachineTimeMonitoring;
    }

    public boolean isMachineOperationTimeMonitoring() {
        return isMachineOperationTimeMonitoring;
    }

    public boolean isCoolantLevelMonitoring() {
        return isCoolantLevelMonitoring;
    }

    public boolean isCuttingForceMonitoring() {
        return isCuttingForceMonitoring;
    }

    public boolean isVibrationMonitoring() {
        return isVibrationMonitoring;
    }

    public void setOnMachine(boolean onMachine) {
        isOnMachine = onMachine;
    }

    public void setStartMachine(boolean startMachine) {
        isStartMachine = startMachine;
    }

    public void setTemperatureMonitoring(boolean temperatureMonitoring) {
        isTemperatureMonitoring = temperatureMonitoring;
    }

    public void setMachineTimeMonitoring(boolean machineTimeMonitoring) {
        isMachineTimeMonitoring = machineTimeMonitoring;
    }

    public void setMachineOperationTimeMonitoring(boolean machineOperationTimeMonitoring) {
        isMachineOperationTimeMonitoring = machineOperationTimeMonitoring;
    }

    public void setCoolantLevelMonitoring(boolean coolantLevelMonitoring) {
        isCoolantLevelMonitoring = coolantLevelMonitoring;
    }

    public void setCuttingForceMonitoring(boolean cuttingForceMonitoring) {
        isCuttingForceMonitoring = cuttingForceMonitoring;
    }

    public void setVibrationMonitoring(boolean vibrationMonitoring) {
        isVibrationMonitoring = vibrationMonitoring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusMachineAgent that = (StatusMachineAgent) o;

        if (isCoolantLevelMonitoring != that.isCoolantLevelMonitoring) return false;
        if (isCuttingForceMonitoring != that.isCuttingForceMonitoring) return false;
        if (isMachineOperationTimeMonitoring != that.isMachineOperationTimeMonitoring) return false;
        if (isMachineTimeMonitoring != that.isMachineTimeMonitoring) return false;
        if (isOnMachine != that.isOnMachine) return false;
        if (isStartMachine != that.isStartMachine) return false;
        if (isTemperatureMonitoring != that.isTemperatureMonitoring) return false;
        if (isVibrationMonitoring != that.isVibrationMonitoring) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (isOnMachine ? 1 : 0);
        result = 31 * result + (isStartMachine ? 1 : 0);
        result = 31 * result + (isTemperatureMonitoring ? 1 : 0);
        result = 31 * result + (isMachineTimeMonitoring ? 1 : 0);
        result = 31 * result + (isMachineOperationTimeMonitoring ? 1 : 0);
        result = 31 * result + (isCoolantLevelMonitoring ? 1 : 0);
        result = 31 * result + (isCuttingForceMonitoring ? 1 : 0);
        result = 31 * result + (isVibrationMonitoring ? 1 : 0);
        return result;
    }
}
