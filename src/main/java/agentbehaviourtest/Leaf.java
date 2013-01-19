package agentbehaviourtest;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * User: janus
 * Date: 12-11-07
 * Time: 14:28
 */
public class Leaf extends DefaultMutableTreeNode {
    private String text;
    private boolean isSelected;

    public Leaf(String text, boolean isSelected) {
        super();
        this.text = text;
        this.isSelected = isSelected;
    }

    public String getText() {
        return text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public String toString() {
        return getText();
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
