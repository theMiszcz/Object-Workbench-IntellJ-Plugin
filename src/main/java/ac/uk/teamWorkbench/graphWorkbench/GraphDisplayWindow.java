package ac.uk.teamWorkbench.graphWorkbench;

import ac.uk.teamWorkbench.SourceFileUtils;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;

public class GraphDisplayWindow {

    private JPanel content;
    private GraphPanel graphPanel;

    public GraphDisplayWindow(Project project) {
        //Create and populate graphPanel
        graphPanel = new GraphPanel();
        refreshGraph(project);
        JBScrollPane scrollPane = new JBScrollPane(graphPanel);

        //create and add action listener for refresh button and panel
        JButton refresh = new JButton("Refresh Graph");
        refresh.addActionListener(e -> refreshGraph(SourceFileUtils.getInstance().getProject()));
        JPanel refreshingPanel = new JPanel();
        refreshingPanel.add(refresh);

        //add elements to the content window
        content.add(refreshingPanel, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshGraph(Project project) {
        graphPanel.build(project);
    }

    public JPanel getContent() {
        return content;
    }

}
