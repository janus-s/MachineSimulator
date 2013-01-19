package agentbehaviourtest;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * User: janus
 * Date: 12-11-07
 * Time: 13:17
 */
public class TestTreeEditor extends AbstractCellEditor implements TreeCellEditor {
    private JTree tree;
    private TestTreeRenderer renderer;
    private Leaf editingLeaf;

    public TestTreeEditor(JTree tree) {
        this.tree = tree;
        renderer = (TestTreeRenderer) tree.getCellRenderer();
    }

    public Component getTreeCellEditorComponent(JTree tree,
                                                Object value,
                                                boolean selected,
                                                boolean expanded,
                                                boolean leaf,
                                                int row) {
        Component component = renderer.getTreeCellRendererComponent(tree, value, selected,
                expanded, leaf, row, true);
        if (leaf) {
            editingLeaf = (Leaf) value;
            JCheckBox checkBox = ((JCheckBox) component);
            checkBox.setSelected(editingLeaf.isSelected());
//            addItemListener(checkBox);
            addChangeListener(checkBox);
        }
        return component;
    }

    private void addChangeListener(JCheckBox checkBox) {
        if (checkBox.getChangeListeners().length != 0)
            return;
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                System.out.println("editingLeaf: " + editingLeaf.getText() + ", checkBox: " + ((JCheckBox)changeEvent.getSource()).getText());
                if (stopCellEditing()) {
                    fireEditingStopped();
                }
            }
        };
    }

    private void addItemListener(JCheckBox checkBox) {
        if (checkBox.getItemListeners().length != 0) return;
        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (((JCheckBox)itemEvent.getSource()).getText().equals(editingLeaf.getText())) {
                    System.out.println("editingLeaf: " + editingLeaf.getText() + ", checkBox: " + ((JCheckBox)itemEvent.getSource()).getText());
                }
                if (stopCellEditing()) {
                    fireEditingStopped();
                }
            }
        };
        checkBox.addItemListener(itemListener);
    }

    public Object getCellEditorValue() {
        JCheckBox checkBox = renderer.getLeafRenderer();
        editingLeaf.setSelected(checkBox.isSelected());
        return editingLeaf;
    }

    public boolean isCellEditable(EventObject event) {
        boolean result = false;
        if (event instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent) event;
            TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
            if (path != null) {
                Object node = path.getLastPathComponent();
                if ((node != null) && (node instanceof DefaultMutableTreeNode))
                    result = ((DefaultMutableTreeNode)node).isLeaf();
            }
        }
        return result;
    }
}
