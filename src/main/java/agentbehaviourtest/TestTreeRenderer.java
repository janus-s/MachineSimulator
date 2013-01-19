package agentbehaviourtest;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

/**
 * User: janus
 * Date: 12-11-07
 * Time: 10:31
 */
public class TestTreeRenderer implements TreeCellRenderer {
    private DefaultTreeCellRenderer nonLeafRenderer;
    private JCheckBox leafRenderer;
    private ImageIcon openAndCloseIcon;
    private Color selectionForeground, selectionBackground,
            textForeground, textBackground;

    public TestTreeRenderer() {
        super();
        leafRenderer = new JCheckBox();
        nonLeafRenderer = new DefaultTreeCellRenderer();
        openAndCloseIcon = createScalableImageIcon("computer_icon.jpeg", "some picture", 32, 32);
//        Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
//        leafRenderer.setFocusPainted((booleanValue != null) && (booleanValue));
        selectionForeground = UIManager.getColor("Tree.selectionForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");
        textForeground = UIManager.getColor("Tree.textForeground");
        textBackground = UIManager.getColor("Tree.textBackground");
    }

    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        if (!leaf) {
            return createNonLeafRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        } else {
            return createLeafRenderer(tree, (Leaf)value, selected);
        }

    }

    private DefaultTreeCellRenderer createNonLeafRenderer() {
        nonLeafRenderer.setClosedIcon(openAndCloseIcon);
        nonLeafRenderer.setOpenIcon(openAndCloseIcon);
        return nonLeafRenderer;
    }

    private JCheckBox createLeafRenderer(JTree tree, Leaf leaf, boolean selected) {
        setColorInLeafRenderer(selected);
        leafRenderer.setText(leaf.getText());
        leafRenderer.setSelected(leaf.isSelected());
        leafRenderer.setEnabled(tree.isEnabled());
        return leafRenderer;
    }

    private void setColorInLeafRenderer(boolean selected) {
        if (selected) {
            leafRenderer.setForeground(selectionForeground);
            leafRenderer.setBackground(selectionBackground);
        } else {
            leafRenderer.setForeground(textForeground);
            leafRenderer.setBackground(textBackground);
        }
    }

    private ImageIcon createScalableImageIcon(String path, String description, int width, int height) {
        ImageIcon icon = createImageIcon(path, description);
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
    }

    private ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public JCheckBox getLeafRenderer() {
        return leafRenderer;
    }
}
