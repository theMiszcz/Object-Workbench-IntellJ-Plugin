package ac.uk.teamWorkbench.graphWorkbench;

/*
 * Copyright (c) 2020. All rights reserved.
 * @Author Kacper Szymański
 *  Description: GraphPanel responsible for storing, generating and perform action of graph in graphWindow
 */

import com.intellij.openapi.project.Project;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;


@SuppressWarnings("CanBeFinal")
public class GraphPanel extends JPanel {

    private ArrayList<Object> graphElements;
    private KlassController klassController;

    private mxGraph graph;

    public GraphPanel() {
        klassController = new KlassController();
        graphElements = new ArrayList<>();
    }


    /**
     * Clear all components, and populate them with new data from @param
     *
     * @param project current project
     */
    public void build(Project project) {
        //clear panel
        removeAll();
        graphElements.clear();
        klassController.clearKlasses();
        graph = new mxGraph();

        //populate panel
        setSize(750, 750);
        klassController.populateKlasses(project);
        add(addGraph());
    }

    @NotNull
    public mxGraphComponent addGraph() {

        getStylesheet(graph);
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            createAnVertexes(graph, parent);
            createAnInheritanceEdges(graph, parent);
            createAnRealizationEdges(graph, parent);
            createAnCompositionEdges(graph, parent);
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = addComponentActionListener(graph);
        new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());

        graphComponent.setEnabled(false);
        return graphComponent;
    }

    @NotNull
    private mxGraphComponent addComponentActionListener(mxGraph graph) {

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.getMaximumSize().setSize(400, 400);
        graphComponent.setEnterStopsCellEditing(true);

        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Object target = graphComponent.getCellAt(mouseEvent.getX(), mouseEvent.getY());
                if (target != null) {
                    System.out.println("mouseEvent = " + mouseEvent);
                    //  JBPopupFactory.getInstance().createMessage("You click on : "+graph.getLabel(target)).showInFocusCenter();
                }
            }
        });

        return graphComponent;
    }

    private void createAnVertexes(@NotNull mxGraph graph, Object parent) {
        try {
            for (Klass klass : klassController.getKlasses())
                graphElements.add(graph.insertVertex(parent,
                        klass.getName(),            // id of vertex
                        klass.getDisplayName(),            // Text in the square
                        25,             // x position of top left corner
                        25,             // y position of top left corner
                        85,        // width of box
                        45,         // height of box
                        (klass.getType().equals("interface")) ? "fillColor=#ffcc66"
                                : (klass.getType().equals("enum")) ? "fillColor=#33cccc"
                                : ""));     //stylesheet color
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAnInheritanceEdges(@NotNull mxGraph graph, Object parent) {

        for (Klass klass : klassController.getKlasses()) {

            int childID = getItemID(klass.getName());
            int parentID = getItemID(klass.getParentName());

            if (parentID >= 0 && childID >= 0)
                graph.insertEdge(parent,            //parent object
                        "e",        // id of edge
                        "inherit",              // text on that edge
                        graphElements.get(childID),     // starting point
                        graphElements.get(parentID),    // ending point
                        "");                        // style of edge
        }
    }

    private void createAnRealizationEdges(@NotNull mxGraph graph, Object parent) {

        for (Klass klass : klassController.getKlasses()) {

            for (String father : klass.getImplementsList()) {

                int childID = getItemID(klass.getName());
                int parentID = getItemID(father);

                if (parentID >= 0 && childID >= 0)
                    graph.insertEdge(parent,
                            "i",
                            "implements",
                            graphElements.get(parentID),
                            graphElements.get(childID),
                            "dashed=true");
            }
        }
    }

    private int getItemID(String name) {
        return graphElements.stream().filter(cell -> ((mxCell) cell).getId().equals(name)).findFirst().map(cell -> graphElements.indexOf(cell)).orElse(-1);
    }

    private void createAnCompositionEdges(@NotNull mxGraph graph, Object parent) {

        for (Klass klass : klassController.getKlasses())
            klass.getFieldsList().forEach(father -> {

                int childID = getItemID(klass.getName());
                int parentID = getItemID(father);

                if (parentID >= 0 && childID >= 0)
                    graph.insertEdge(parent,
                            "f",
                            "belongs to",
                            graphElements.get(parentID),
                            graphElements.get(childID),
                            "endFill=1;endArrow=diamond");
            });
    }

    private void getStylesheet(mxGraph graph) {
        mxStylesheet style = new mxStylesheet();
        for (Map.Entry<String, Map<String, Object>> entry : style.getStyles().entrySet()) {
            for (Map.Entry<String, Object> objects : entry.getValue().entrySet()) {
                if (objects.getKey().equals("fillColor")) objects.setValue("#99ff33");
                if (objects.getKey().equals("strokeColor")) objects.setValue("#3d3d5c");
                if (objects.getKey().equals("fontColor")) objects.setValue("#00264d");
            }
        }
        style.getDefaultEdgeStyle().put("endSize", 10);
        style.getDefaultEdgeStyle().put("endFill", 0);
        style.getDefaultEdgeStyle().put("fillColor", "#ffffff");
        style.getDefaultEdgeStyle().put("endArrow", "block");
        graph.setStylesheet(style);
    }
}
