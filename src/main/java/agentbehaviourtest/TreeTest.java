package agentbehaviourtest;

import javax.swing.*;
import javax.swing.tree.*;

/**
 * User: janus
 * Date: 12-11-07
 * Time: 07:49
 */
public class TreeTest extends JFrame {
    private JTree tree;
    private JScrollPane scroll;

    public TreeTest() {
        initComponent();
        createLayout();
    }

    private void initComponent() {
        tree = new JTree(getTreeModel());
        tree.setRootVisible(false);
//        tree.setCellRenderer(createRenderer());
        tree.setCellRenderer(new TestTreeRenderer());
        tree.setCellEditor(new TestTreeEditor(tree));
        tree.setEditable(true);
        scroll = new JScrollPane(tree);
    }

    private TreeModel getTreeModel() {
        MutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultTreeModel model = new DefaultTreeModel(root);
        DefaultMutableTreeNode janusz = new DefaultMutableTreeNode("Janusz");
        model.insertNodeInto(janusz, root, 0);
        model.insertNodeInto(new Leaf("Ula", false), janusz, 0);
        model.insertNodeInto(new Leaf("Nadziejka", false), janusz, 1);

        DefaultMutableTreeNode krzysiek = new DefaultMutableTreeNode("Krzysiek");
        model.insertNodeInto(krzysiek, root, 1);
        model.insertNodeInto(new Leaf("Małgosia", false), krzysiek, 0);
        model.insertNodeInto(new Leaf("Józek", false), krzysiek, 1);
        return model;
    }

    private TreeCellRenderer createRenderer() {
        ImageIcon icon = createImageIcon("computer_icon.jpeg", "some picture");
        icon = new ImageIcon(icon.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH));
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setOpenIcon(icon);
        renderer.setClosedIcon(icon);
        return renderer;
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

    private void createLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scroll)
                        .addContainerGap()
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scroll)
                        .addContainerGap()
        );
    }
}
