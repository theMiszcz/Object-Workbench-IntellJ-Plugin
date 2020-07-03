package ac.uk.teamWorkbench.objectWorkbench.externalLibraryLoader;

import ac.uk.teamWorkbench.SourceFileUtils;
import ac.uk.teamWorkbench.objectWorkbench.ControllerTemplate;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ExternalLibraryController extends ControllerTemplate {

    private static final Logger LOGGER = Logger.getLogger(ExternalLibraryController.class.getName());

    private ExternalLibraryWindow GUI;
    private List<Library> libraryList;

    //TODO change it to show those loaded libraries to the object creation
    // as i load external libraries on startup now
    public ExternalLibraryController(ExternalLibraryWindow GUI) {
        this.GUI = GUI;
        this.libraryList = new ArrayList<>();
        start();
    }

    public void init() {
        LibraryTable projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(
                SourceFileUtils.getInstance().getProject());
        libraryList = Arrays.asList(projectLibraryTable.getLibraries());
        List<String> libraryNames = new ArrayList<>();

        libraryList.forEach(e -> libraryNames.add(e.getName()));

        DefaultListModel<String> listModel = GUI.getLibraryNamesListModel();
        listModel.addAll(libraryNames);
    }

    public void addListeners() {
        GUI.getAddLibraryButton().addActionListener(e -> {
            List<String> selectedLibraries = GUI.getLibraryNamesList().getSelectedValuesList();
            if(!selectedLibraries.isEmpty()) {
                GUI.getLoadedLibrariesNamesListModel().addAll(selectedLibraries);
            }
        });

        GUI.getRemoveLibraryButton().addActionListener(e -> {
            List<String> loadedSelectedLibraries = GUI.getLoadedLibrariesList().getSelectedValuesList();
            if(!loadedSelectedLibraries.isEmpty()) {
                for (String loadedSelectedLibrary : loadedSelectedLibraries) {
                    GUI.getLoadedLibrariesNamesListModel().removeElement(loadedSelectedLibrary);
                }
            }
        });
    }

    public List<Library> getLibraryList() {
        return libraryList;
    }

}
