package gui;

import jade.core.AID;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * User: janus
 * Date: 12-09-26
 * Time: 20:54
 */
public class MachineTableModel extends AbstractTableModel {
    private List<AID> machineAgents;
    private String[] headers = new String[] {"Lista maszyn"};

    public MachineTableModel() {
        machineAgents = new ArrayList<AID>();
    }

    public int getRowCount() {
        return machineAgents.size();
    }

    public int getColumnCount() {
        return headers.length;
    }

    public Object getValueAt(int row, int column) {
        return machineAgents.get(row).getLocalName();
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    public void setAgents(List<AID> agents) {
        for (AID aid : agents) {
            if (!machineAgents.contains(aid)) {
                machineAgents.add(aid);
                fireTableDataChanged();
            }
        }
    }

    public void addAgent(AID agent) {
        machineAgents.add(agent);
    }

    public AID getSelectedAgent(int row) {
        return machineAgents.get(row);
    }
}
