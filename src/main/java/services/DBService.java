package services;

import machine.Machine;
import machine.MachineStatus;
import machine.Param;
import machine.ParamDict;

/**
 * User: janus
 * Date: 12-12-04
 * Time: 19:55
 */
public class DBService {
    private DBMachineService dbMachineService;
    private DBStatusService dbStatusService;
    private DBParamService dbParamService;

    public DBService() {
        dbMachineService = new DBMachineService();
        dbStatusService = new DBStatusService();
        dbParamService = new DBParamService();
    }

    public void addMachine(Machine machine) {
        dbMachineService.addMchine(machine);
    }

    public void updateMachine(Machine machine) {
        dbMachineService.updateMachine(machine);
    }

    public void removeMachine(Machine machine) {
        dbMachineService.removeMachine(machine);
    }

    public void saveStatus(MachineStatus machineStatus) {
        dbStatusService.saveStatus(machineStatus);
    }

    public void addParamDict(String paramName) {
        dbParamService.addParamDict(paramName);
    }

    public void updateParamDict(ParamDict paramDict) {
        dbParamService.updateParamDict(paramDict);
    }

    public void removeParamDict(ParamDict paramDict) {
        dbParamService.removeParamDict(paramDict);
    }

    public void saveParam(Param param) {
        dbParamService.saveParam(param);
    }
}
