package ac.uk.teamWorkbench.objectWorkbench.externalLibraryLoader;

import ac.uk.teamWorkbench.objectWorkbench.ControllerTemplate;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ExternalLibraryWindow extends DialogWrapper {

    private ControllerTemplate controller;
    private JPanel content;
    private JBList<String> libraryNamesList;
    private JBList<String> loadedLibrariesList;

    private JButton removeLibraryButton;
    private JButton addLibraryButton;
    private DefaultListModel<String> libraryNamesListModel;
    private DefaultListModel<String> loadedLibrariesNamesListModel;

    private void createUIComponents() {
        libraryNamesListModel = new DefaultListModel<>();
        loadedLibrariesNamesListModel = new DefaultListModel<>();

        libraryNamesList = new JBList<>(libraryNamesListModel);
        loadedLibrariesList = new JBList<>(loadedLibrariesNamesListModel);
    }

    public ExternalLibraryWindow(boolean canBeParent) {
        super(canBeParent);
        init();
        setTitle("External Library Chooser");
        controller = new ExternalLibraryController(this);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return content;
    }

    public DefaultListModel<String> getLibraryNamesListModel() {
        return libraryNamesListModel;
    }

    public DefaultListModel<String> getLoadedLibrariesNamesListModel() {
        return loadedLibrariesNamesListModel;
    }

    public JBList<String> getLibraryNamesList() {
        return libraryNamesList;
    }

    public JBList<String> getLoadedLibrariesList() {
        return loadedLibrariesList;
    }

    public ControllerTemplate getController(){
        return controller;
    }

    public JButton getRemoveLibraryButton() {
        return removeLibraryButton;
    }

    public JButton getAddLibraryButton() {
        return addLibraryButton;
    }

}
