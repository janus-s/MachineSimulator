package gui;

/**
 * User: janus
 * Date: 12-11-05
 * Time: 08:34
 */
class CheckBoxNode {
    private   String text;
    private  boolean selected;

    public CheckBoxNode(String text, boolean isSelected) {
        this.text = text;
        this.selected = isSelected;
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

    @Override
    public String toString() {
        return text;
    }
}
