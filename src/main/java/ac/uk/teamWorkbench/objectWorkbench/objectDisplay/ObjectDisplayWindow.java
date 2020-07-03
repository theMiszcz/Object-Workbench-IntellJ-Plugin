package ac.uk.teamWorkbench.objectWorkbench.objectDisplay;

import javax.swing.*;

/**
 * ObjectDisplayWindow
 *
 * The main GUI form for the ObjectWorkbench.
 */
public class ObjectDisplayWindow {

    private JPanel contentWindow;
    private JPanel leftPane;
    private JPanel rightPane;
    private JSplitPane splitPane;
    private JTabbedPane tabbedPane;
    private JButton executeButton;
    private JButton compileButton;

    private WorkbenchController controller;

    public ObjectDisplayWindow() {
        controller = new WorkbenchController(this);
        init();
    }

    private void init() {
        controller.addMousePressedListener(this.tabbedPane);
        controller.addButtonListener(this.executeButton, this.compileButton);
    }

    public JTabbedPane getTabbedPane() { return this.tabbedPane; }
    public JSplitPane getSplitPane() { return  this.splitPane; }
    public JPanel getContentWindow() { return this.contentWindow; }
}