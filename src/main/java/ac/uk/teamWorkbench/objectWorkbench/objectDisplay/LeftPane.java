package ac.uk.teamWorkbench.objectWorkbench.objectDisplay;

import net.miginfocom.layout.Grid;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class LeftPane extends JPanel {

    private GridBagConstraints cons;
    private static LeftPane instance;
    private ArrayList<JPanel> panelArray;

    public LeftPane(){

        cons = new GridBagConstraints();
        panelArray = new ArrayList<>();

        cons.weighty = 0.1;
        cons.weighty = 0.1;
        cons.insets = new Insets(10,10 ,0,0);
    }

    public static LeftPane getInstance(){
        if(instance == null) instance = new LeftPane();
        return instance;
    }

    /**
     * Draws JLabels to a JPanel and returns the JPanel
     */
    public JPanel drawLabels(Object[] params, Class<?>[] paramTypes, Field[] fields){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        for(int i = 0; i < params.length; i++){
            cons.gridx = 0;
            cons.gridy = i;
            cons.anchor = GridBagConstraints.FIRST_LINE_END;
            panel.add(new JLabel("Type : " + paramTypes[i].getSimpleName() + "   "), cons);

            cons.gridx = 1;
            cons.gridy = i;
            cons.anchor = GridBagConstraints.FIRST_LINE_START;
            panel.add(new JLabel(fields[i].getName() + " : " + params[i] + "   "), cons);
        }

        return panel;
    }

    /**
     * Stores a JPanel to the JPanel ArrayList
     */
    public void storePanel(JPanel panel){
        panelArray.add(panel);
    }
    public void storePanel(JPanel panel, int index){ panelArray.add(index, panel);}

    /**
     * Retrieves a JPanel from the JPanel ArrayList by index
     */
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
}
