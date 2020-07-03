package ac.uk.teamWorkbench.objectWorkbench.objectDisplay;

import javax.swing.*;
import java.awt.*;

/**
 * DialogFactory
 *
 * Provides various quick-use dialog window builders for the ObjectWorkbench.
 */
public class DialogFactory {

    public DialogFactory() {
    }

    /**
     * Gets user confirmation - 0 = yes, 1 = no, 2 = cancel
     * @param parent - The parent component of the dialog
     * @param message - The message in the dialog
     * @param title - The title of the dialog
     * @return answer from the user
     */
    public int getUserConfirmation(Component parent, String message, String title) {
        return JOptionPane.showConfirmDialog(parent,
                message, title,JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Dialog for changing the name of a tab
     * @return the new name of the tab (String)
     */
    public String changeTabNameDialog(String tabTitle, Component parent) {
        return (String)JOptionPane.showInputDialog(parent,
                "Enter New Title: ",
                "Title",
                JOptionPane.PLAIN_MESSAGE,
                null, null,
                tabTitle);
    }

    /**
     * Displays a warning dialog
     * @param parent - the parent of the dialog
     * @param message - the message in the dialog
     * @param title - the title of the dialog
     */
    public void displayWarningDialog(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
