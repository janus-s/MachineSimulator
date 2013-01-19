package agents;

/**
 * User: janus
 * Date: 12-11-03
 * Time: 21:55
 */
public enum MonitoringParams {
    TEMPERATURE("temperature", "Temperatura obrabiarki wynosi: "),
    MACHINE_TIME("machineTime", "Czas pracy obrabiarki (wraz z postojami) wynosi: "),
    MACHINE_OPERATION_TIME("machineOperationTime", "Czas pracy obrabiarki wynosi: "),
    COOLANT_LEVEL("coolantLevel", "Poziom chłodziwa obrabiarki wynosi: "),
    CUTTING_FORCE("cuttingForce", "Siła skrawania obrabiarki wynosi: "),
    VIBRATION("vibration", "Siła drgań obrabiarki wynosi: ");

    private final String param;
    private final String suffixAgent = "Agent";
    private final String comment;

    private MonitoringParams(String param, String comment) {
        this.param = param;
        this.comment = comment;
    }

    public String getParam() {
        return param;
    }

    public String getAgent() {
        return param + suffixAgent;
    }

    public String getComment() {
        return comment;
    }
}
