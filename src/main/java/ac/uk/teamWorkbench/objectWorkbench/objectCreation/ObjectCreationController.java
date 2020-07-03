package ac.uk.teamWorkbench.objectWorkbench.objectCreation;

import ac.uk.teamWorkbench.objectWorkbench.ControllerTemplate;
import ac.uk.teamWorkbench.workbenchRuntime.ClassReflection;
import ac.uk.teamWorkbench.workbenchRuntime.ObjectPool;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ObjectCreationController extends ControllerTemplate {

    private static final Logger LOGGER = Logger.getLogger(ObjectCreationController.class.getName());

    private ObjectCreationWindow GUI;
    private ObjectPool objectPool;

    //HashMap of classes in the Project
    private Map<String, ClassReflection> classReflectionMap;
    //TODO its meant to hold one's constructor parameters
    private Map<Integer, List<JTextField>> mapConstructorParameters;

    /**
     * Constructor
     *
     * @param GUI window class part that is passed to this controller
     */
    public ObjectCreationController(ObjectCreationWindow GUI) {
        this.GUI = GUI;
        this.objectPool = ObjectPool.getInstance();
        this.classReflectionMap = objectPool.getClassReflectionMap();
        this.mapConstructorParameters = new HashMap<>();

        start();
    }

    @Override
    public void init() {
        DefaultListModel<String> javaListModel = GUI.getJavaClassListModel();
        javaListModel.clear();
        classReflectionMap.forEach((k, v) -> javaListModel.addElement(v.getClassName()));
    }

    /**
     * Add Event Listeners
     */
    @Override
    public void addListeners() {
        JBList<String> classList = GUI.getClassListJBList();
        classList.addListSelectionListener(e -> {
            if (!classList.getValueIsAdjusting() &&
                    classList.getSelectedValue() != null) {

                populateMethodList(classList.getSelectedValue());
                populateVariableList(classList.getSelectedValue());
                populateConstructorList();
            }
        });

        JTabbedPane constructorTabList = GUI.getConstructorsTabList();
        constructorTabList.addChangeListener(e -> {
            if (constructorTabList.getSelectedIndex() != -1) {
//                populateParameterValues();
            }
        });
    }

    private void populateMethodList(String key) {
        DefaultListModel<String> javaMethodsListModel = GUI.getJavaMethodsListModel();
        javaMethodsListModel.clear();
        javaMethodsListModel.addAll(classReflectionMap.get(key).getMethodListAsText());
    }

    private void populateVariableList(String key) {
        DefaultListModel<String> javaVariablesListModel = GUI.getJavaVariablesListModel();
        javaVariablesListModel.clear();
        javaVariablesListModel.addAll(classReflectionMap.get(key).getVariableListAsText());
    }

    private void populateConstructorList() {
        //Get reference from GUI
        JTabbedPane constructorsTab = GUI.getConstructorsTabList();
        String className = GUI.getSelectedClassName();
        //Clear Tab
        constructorsTab.removeAll();
        //Get list of constructors as list
        List<String> constructorListAsText = classReflectionMap.get(className).getConstructorListAsText();

        mapConstructorParameters.clear();
        for (int i = 0; i < constructorListAsText.size(); i++) {
            ArrayList<JTextField> textFieldList = new ArrayList<>();
            List<String> constructorParameters = classReflectionMap.get(className).getParameterListAsText(i);
            JPanel panel = createConstructorTab();

            createPanelElement(constructorParameters, panel, textFieldList);
            constructorsTab.addTab(constructorListAsText.get(i), panel);

            mapConstructorParameters.put(i, textFieldList);
        }
    }

    /**
     * Creates a constructor tab
     *
     * @return constructor tab
     */
    private JPanel createConstructorTab() {
        JPanel panel = new JPanel();
        FlowLayout layout = new FlowLayout();
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(100, 400));
        panel.setMaximumSize(new Dimension(100, 900));
        return panel;
    }

    private void createPanelElement(List<String> parameterList, JPanel panel, ArrayList<JTextField> textFieldList) {
        JLabel label;
        JTextField textField;
        for (String parameterName : parameterList) {
            label = new JLabel(parameterName);
            textField = new JTextField();
            textFieldList.add(textField);

            panel.add(textField);
            panel.add(label);
        }
    }

    public Map<Integer, List<JTextField>> getMapConstructorParameters() {
        return mapConstructorParameters;
    }

}
