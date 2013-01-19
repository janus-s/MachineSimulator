package gui;

import jade.core.AID;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * User: janus
 * Date: 12-11-04
 * Time: 21:24
 */
public class AgentTreeNode extends DefaultMutableTreeNode {
    private AID aid;

    public AgentTreeNode(AID aid) {
        super(aid);
        this.aid = aid;
        addParameterTree();
    }

    private void addParameterTree() {
        add(new DefaultMutableTreeNode(new CheckBoxNode("temperatura", false)));
        add(new DefaultMutableTreeNode(new CheckBoxNode("poziom chłodziwa", false)));
        add(new DefaultMutableTreeNode(new CheckBoxNode("czas pracy", false)));
    }

    @Override
    public String toString() {
        return aid.getLocalName();
    }
}
