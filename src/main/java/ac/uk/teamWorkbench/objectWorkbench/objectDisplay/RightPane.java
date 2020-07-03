package ac.uk.teamWorkbench.objectWorkbench.objectDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class RightPane extends JPanel implements ActionListener {

    private static RightPane instance;
    private ArrayList<JPanel> panelArray;
    private RightPaneListener listener;

    public RightPane(){
        panelArray = new ArrayList<>();

    }

    public static RightPane getInstance(){
        if(instance == null) instance = new RightPane();
        return instance;
    }

    public JPanel drawButtons(Method[] methods){
        JPanel panel = new JPanel();
        for(int i = 0; i < methods.length; i++){
            String methodName = methods[i].getName();
            if(methodName.equals("main")){ break; }
            else{
                JButton button = new JButton(methodName);
                button.addActionListener(this);
                panel.add(button);
                panel.add(new JLabel(" "));
            }
        }
        return panel;
    }

    public void storePanel(JPanel panel){
        panelArray.add(panel);
    }

    public JPanel getPanel(int index){
        if(panelArray.isEmpty()){
            return new JPanel();
        }
        return panelArray.get(index);
    }

    public void removePanel(int index){
        panelArray.remove(index);
    }

    public void removeAllPanels(){
        panelArray.clear();
    }

    public void addButtonListener(RightPaneListener listener){
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(listener != null){
            listener.buttonEventOccurred(actionEvent);
        }
    }
}
