package ac.uk.teamWorkbench.graphWorkbench;

/* **********
 * Copyright (c) 2020. All rights reserved.
 * @Author Kacper
 *  Description: Controller class for Klass entity. Storing List of Klasses and populate it.
 * **********/

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.Arrays;

import static ac.uk.teamWorkbench.SourceFileUtils.getAllPSIFiles;

public class KlassController {

    ArrayList<Klass> klasses;

    public KlassController() {
        klasses = new ArrayList<>();
    }

    public ArrayList<Klass> getKlasses() {
        return klasses;
    }

    public void addKlass(Klass klass) {
        klasses.add(klass);
    }

    public void populateKlasses(Project project) {
        for (PsiFile psiFile : getAllPSIFiles(project))
            for (PsiElement file : psiFile.getChildren()) {
                if (file.getContext() == null) continue;
                if (file.toString().contains("PsiClass")) {
                    Klass klass = new Klass(file.toString().split(":")[1]);
                    for (PsiElement child :
                            file.getChildren()) {
                        if (child.toString().contains(("PsiReferenceList"))) {
                            if (child.getChildren().length < 1) continue;
                            for (PsiElement grandchild :
                                    child.getChildren())
                                if (grandchild.toString().contains("PsiJavaCodeReferenceElement")) {
                                    if (grandchild.getText().isEmpty()) continue;
                                    if (child.getText().contains("extends")) klass.setParentName(grandchild.getText());
                                    if (child.getText().contains("implements"))
                                        klass.addImplementsListItem(grandchild.getText());
                                }
                        }
                        if (child.toString().contains("PsiField"))
                            if (child.getText().contains("<") || child.getText().contains(">"))
                                klass.addFieldsListItem(child.getText().split("[<>]")[1]);
                            else klass.addFieldsListItem(clearPsiField(child.getText()).get(0));
                        if (child.toString().contains("PsiKeyword:interface")) klass.setType("interface");
                        if (child.toString().contains("PsiKeyword:enum")) klass.setType("enum");
                    }
                    addKlass(klass);
                }
            }
    }

    private ArrayList<String> clearPsiField(String text) {
        ArrayList<String> split = new ArrayList<>(Arrays.asList(text.split("\\s")));
        split.removeIf(s -> s.equals("public") || s.equals("private") || s.equals("protected"));
        split.removeIf(s -> s.equals("static"));
        split.removeIf(s -> s.equals("final"));
        split.removeIf(s -> s.equals("abstract"));
        return split;
    }

    public void clearKlasses() {
        klasses.clear();
    }
}
