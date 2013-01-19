package gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

/**
 * User: janus
 * Date: 12-11-05
 * Time: 08:41
 */
public class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {
  CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
    Logger logger = LoggerFactory.getLogger(getClass());

  ChangeEvent changeEvent = null;

  JTree tree;

  public CheckBoxNodeEditor(JTree tree) {
    this.tree = tree;
  }

  public Object getCellEditorValue() {
    JCheckBox checkbox = renderer.getLeafRenderer();
    CheckBoxNode checkBoxNode = new CheckBoxNode(checkbox.getText(), checkbox.isSelected());
      logger.info("getCellEditorValue:" + checkbox.isSelected());
    return checkBoxNode;
  }

  public boolean isCellEditable(EventObject event) {
    boolean returnValue = false;
    if (event instanceof MouseEvent) {
      MouseEvent mouseEvent = (MouseEvent) event;
      TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
      if (path != null) {
        Object node = path.getLastPathComponent();
        if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
          DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
          Object userObject = treeNode.getUserObject();
          returnValue = ((treeNode.isLeaf()) && (userObject instanceof CheckBoxNode));
        }
      }
    }
    return returnValue;
  }

  public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected,
      boolean expanded, boolean leaf, int row) {

    Component editor = renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf,
        row, true);

    ItemListener itemListener = new ItemListener() {
      public void itemStateChanged(ItemEvent itemEvent) {
        if (stopCellEditing()) {
          fireEditingStopped();
        }
      }
    };
    if (editor instanceof JCheckBox) {
        final JCheckBox checkBox = (JCheckBox)editor;
        final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        checkBox.addItemListener(itemListener);
        checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                CheckBoxNode userObject = (CheckBoxNode) node.getUserObject();
                if (checkBox.isSelected()) {
                    System.out.println("param: " + userObject.toString() + ", isSelected: " + userObject.isSelected());
                } else {

                }
            }
        });
    }
    return editor;
  }
}
