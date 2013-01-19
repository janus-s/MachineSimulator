package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * User: janus
 * Date: 12-11-05
 * Time: 09:24
 */

class TreeNodeVector<E> extends Vector<E> {
  String name;

  TreeNodeVector(String name) {
    this.name = name;
  }

  TreeNodeVector(String name, E elements[]) {
    this.name = name;
    for (int i = 0, n = elements.length; i < n; i++) {
      add(elements[i]);
    }
  }

  public String toString() {
    return "[" + name + "]";
  }
}

class LeafCellEditor extends DefaultTreeCellEditor {

  public LeafCellEditor(JTree tree, DefaultTreeCellRenderer renderer, TreeCellEditor editor) {
    super(tree, renderer, editor);
  }

  public boolean isCellEditable(EventObject event) {
    boolean returnValue = super.isCellEditable(event);
    if (returnValue) {
      Object node = tree.getLastSelectedPathComponent();
      if ((node != null) && (node instanceof TreeNode)) {
        TreeNode treeNode = (TreeNode) node;
        returnValue = treeNode.isLeaf();
      }
    }
    return returnValue;
  }
}

class CheckBoxNodeTest {
  String text;

  boolean selected;

  public CheckBoxNodeTest(String text, boolean selected) {
    this.text = text;
    this.selected = selected;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean newValue) {
    selected = newValue;
  }

  public String getText() {
    return text;
  }

  public void setText(String newValue) {
    text = newValue;
  }

  public String toString() {
    return getClass().getName() + "[" + text + "/" + selected + "]";
  }
}

class CheckBoxNodeRendererTest implements TreeCellRenderer {
  private JCheckBox leafRenderer = new JCheckBox();

  private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();

  protected JCheckBox getLeafRenderer() {
    return leafRenderer;
  }

  public CheckBoxNodeRendererTest() {
    Font fontValue;
    fontValue = UIManager.getFont("Tree.font");
    if (fontValue != null) {
      leafRenderer.setFont(fontValue);
    }
    Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
    leafRenderer.setFocusPainted((booleanValue != null) && (booleanValue.booleanValue()));
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
      boolean expanded, boolean leaf, int row, boolean hasFocus) {

    Component returnValue;
    if (leaf) {
      String stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, false);
      leafRenderer.setText(stringValue);
      leafRenderer.setSelected(false);
      leafRenderer.setEnabled(tree.isEnabled());
      if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
        if (userObject instanceof CheckBoxNodeTest) {
          CheckBoxNodeTest node = (CheckBoxNodeTest) userObject;
          leafRenderer.setText(node.getText());
          leafRenderer.setSelected(node.isSelected());
        }
      }
      returnValue = leafRenderer;
    } else {
      returnValue = nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded,
          leaf, row, hasFocus);
    }
    return returnValue;
  }
}

class CheckBoxNodeEditorTest extends AbstractCellEditor implements TreeCellEditor {
  CheckBoxNodeRendererTest renderer = new CheckBoxNodeRendererTest();

  ChangeEvent changeEvent = null;

  JTree tree;

  public CheckBoxNodeEditorTest(JTree tree) {
    this.tree = tree;
  }

  public Object getCellEditorValue() {
    JCheckBox checkbox = renderer.getLeafRenderer();
    CheckBoxNodeTest checkBoxNode = new CheckBoxNodeTest(checkbox.getText(), checkbox.isSelected());
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
          returnValue = ((treeNode.isLeaf()) && (userObject instanceof CheckBoxNodeTest));
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
      ((JCheckBox) editor).addItemListener(itemListener);
    }
    return editor;
  }
}

public class CheckBoxNodeTreeSample {
  public static void main(String args[]) {
    JFrame frame = new JFrame("CheckBox Tree");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    CheckBoxNodeTest accessibilityOptions[] = { new CheckBoxNodeTest("A", false),
        new CheckBoxNodeTest("B", true) };
    CheckBoxNodeTest browsingOptions[] = { new CheckBoxNodeTest("C", true), new CheckBoxNodeTest("D", true),
        new CheckBoxNodeTest("E", true), new CheckBoxNodeTest("F", false) };
    Vector<CheckBoxNodeTest> accessVector = new TreeNodeVector<CheckBoxNodeTest>("G", accessibilityOptions);
    Vector<CheckBoxNodeTest> browseVector = new TreeNodeVector<CheckBoxNodeTest>("H", browsingOptions);
    Object rootNodes[] = { accessVector, browseVector };
    Vector<Object> rootVector = new TreeNodeVector<Object>("Root", rootNodes);
    JTree tree = new JTree(rootVector);

    CheckBoxNodeRendererTest renderer = new CheckBoxNodeRendererTest();
    tree.setCellRenderer(renderer);

    tree.setCellEditor(new CheckBoxNodeEditorTest(tree));
    tree.setEditable(true);
    JScrollPane scrollPane = new JScrollPane(tree);
    frame.add(scrollPane, BorderLayout.CENTER);
    frame.setSize(300, 150);
    frame.setVisible(true);
  }
}
