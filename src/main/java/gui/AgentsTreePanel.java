package gui;

import agents.ManagerAgent;
import jade.core.AID;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: janus
 * Date: 12-11-04
 * Time: 20:45
 */
public class AgentsTreePanel extends JPanel {
    private JTree agentsTree;
    private JScrollPane scroll;
    private Map<AID, DefaultMutableTreeNode> nodes;

    public AgentsTreePanel(ManagerAgent managerAgent) {
        initComponent(managerAgent);
        createLayout();
    }

    private void initComponent(ManagerAgent managerAgent) {
        nodes = new HashMap<AID, DefaultMutableTreeNode>();
        TreeNode root = new DefaultMutableTreeNode(managerAgent);
        TreeModel model = new DefaultTreeModel(root);
        agentsTree = new JTree(model);
        agentsTree.setCellRenderer(new CheckBoxNodeRenderer());
        agentsTree.setCellEditor(new CheckBoxNodeEditor(agentsTree));
        agentsTree.setEditable(true);
        agentsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
//        agentsTree.addTreeSelectionListener(new TreeSelectionListener() {
//            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
//                JTree tree = (JTree)treeSelectionEvent.getSource();
//                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
//                if (selectedNode.isLeaf()) {
//                    CheckBoxNode node = (CheckBoxNode)selectedNode.getUserObject();
//                    System.out.println(node.toString() + "isSelected: " + node.isSelected());
//
//                }
//            }
//        });
//        agentsTree.setRootVisible(false);
        scroll = new JScrollPane(agentsTree);
    }

    private void createLayout() {
		GroupLayout panelLayout = new GroupLayout(this);
		this.setLayout(panelLayout);

		panelLayout.setHorizontalGroup(
				panelLayout.createParallelGroup()
				.addComponent(this.scroll));

		panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup()
                        .addComponent(this.scroll));
    }

    public void addNewAgent(AID agent) {
        if (!nodes.containsKey(agent)) {
            DefaultTreeModel model = (DefaultTreeModel) agentsTree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            AgentTreeNode agentNode = new AgentTreeNode(agent);
//            DefaultMutableTreeNode node = new DefaultMutableTreeNode(agent);
            model.insertNodeInto(agentNode, root, root.getChildCount());
            nodes.put(agent, agentNode);
        }
    }

    public void deleteAgent(AID agent) {
        if (nodes.containsKey(agent)) {
            DefaultMutableTreeNode node = nodes.get(agent);
            ((DefaultTreeModel) agentsTree.getModel()).removeNodeFromParent(node);
        }
    }
}
