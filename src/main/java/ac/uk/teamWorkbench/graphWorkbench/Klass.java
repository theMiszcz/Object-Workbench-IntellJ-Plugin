package ac.uk.teamWorkbench.graphWorkbench;

/* **********
 * Copyright (c) 2020. All rights reserved.
 * @Author Kacper
 *  Description: Entity class represents class structure in project.
 ********** */

import java.util.ArrayList;

public class Klass {
    String name;
    String type;
    String parentName;
    ArrayList<String> implementsList;
    ArrayList<String> fieldsList;


    public Klass(String name) {
        setName(name);
        setType("class");
        setParentName("");
        implementsList = new ArrayList<>();
        fieldsList = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList<String> getImplementsList() {
        return implementsList;
    }

    public ArrayList<String> getFieldsList() {
        return fieldsList;
    }

    public void addImplementsListItem(String name) {
        implementsList.add(name);
    }

    public void addFieldsListItem(String name) {
        fieldsList.add(name);
    }

    public String getDisplayName() {
        StringBuilder display = new StringBuilder();
        if (getType().equals("interface")) display.append("<<").append("Interface").append(">>");
        if (getType().equals("enum")) display.append("<<").append("Enum").append(">>");
        display.append("\n").append(getName()).append("\n");
        return display.toString();

    }
}