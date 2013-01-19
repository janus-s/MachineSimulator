package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

/**
 * User: janus
 * Date: 12-11-05
 * Time: 10:36
 */
public class NonLeafNodeRenderer extends JLabel implements TreeCellRenderer {

    public Component getTreeCellRendererComponent(JTree jTree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
        if (!leaf && userObject instanceof AgentTreeNode) {
            AgentTreeNode node = (AgentTreeNode) userObject;
            setText(node.toString());
//            ImageIcon icon = new ImageIcon("computer_icon.jpeg");
//            label = new JLabel(node.toString(), icon, SwingConstants.RIGHT);
//            label = new JLabel(node.toString());
//            add(label);
//            createLayout();
            return this;
        }
        return this;
    }

//    private void createLayout() {
//		GroupLayout panelLayout = new GroupLayout(this);
//		this.setLayout(panelLayout);
//
//		panelLayout.setHorizontalGroup(
//				panelLayout.createParallelGroup()
//				.addComponent(icon)
//                .addComponent(label));
//
//		panelLayout.setVerticalGroup(
//                panelLayout.createParallelGroup()
//                        .addComponent(this.scroll));
//    }
}
