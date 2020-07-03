package ac.uk.teamWorkbench.workbenchRuntime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Class containing all information about one class in the project,
 * it contains all methods and variables that it has, as well as
 * its parent and children.
 */
public class ClassReflection {

    private Class<?> clazz;

    private String className;
    private String packageName;
    private String parentClass;
    private List<Class<?>> childrenList = new ArrayList<>();
    private List<Field> variableList = new ArrayList<>();
    private List<Method> methodList = new ArrayList<>();
    private List<Constructor<?>> constructorList = new ArrayList<>();

    public ClassReflection(String className, Class<?> clazz) {
        this.className = className;
        this.clazz = clazz;
        this.packageName = clazz.getPackage().getName();
    }

    /**
     * Add a method to the list of the object
     *
     * @param methodName Name of the method as a string
     * @return true if addition is successful, false otherwise
     */
    public boolean addMethod(Method methodName) {
        return methodList.add(methodName);
    }

    /**
     * Add a variable to the list of the object
     *
     * @param variableName Name of the variable as a string
     * @return true if addition is successful, false otherwise
     */
    public boolean addVariable(Field variableName) {
        return variableList.add(variableName);
    }

    public boolean addConstructor(Constructor<?> constructorName) {
        return constructorList.add(constructorName);
    }

    ////Getters
    public String getClassName() {
        return className;
    }

    public List<String> getMethodListAsText() {
        List<String> methods = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        methodList.forEach(method -> {
            //Clears string builder
            sb.setLength(0);
            sb.append(method.getReturnType().getSimpleName()).append(" ")
                    .append(method.getName()).append("(");

            for (int i = 0; i < method.getGenericParameterTypes().length; i++) {
                sb.append(method.getParameterTypes()[i].getSimpleName()).append(" ").
                        append(method.getParameters()[i].getName());
                if (i != method.getGenericParameterTypes().length - 1) sb.append(", ");
            }
            sb.append(")");
            methods.add(String.valueOf(sb));
        });
        return methods;
    }

    public List<String> getVariableListAsText() {
        List<String> variables = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        variableList.forEach(variable -> {
            sb.setLength(0);
            sb.append(variable.getType().getSimpleName()).append(" ");
            sb.append(variable.getName());

            variables.add(String.valueOf(sb));
        });

        return variables;
    }

    public List<String> getConstructorListAsText() {
        List<String> constructors = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        constructorList.forEach(constructor -> {
            sb.setLength(0);
            if (constructor.getParameterCount() <= 0) {
                sb.append("No Argument Constructor");
            } else if (constructor.getParameterCount() < 4) {
                sb.append("(");
                for (int i = 0; i < constructor.getParameterCount(); i++) {
                    sb.append(constructor.getParameterTypes()[i].getSimpleName());
                    if (i != constructor.getParameterCount() - 1) sb.append(", ");
                }
                sb.append(") Constructor");
            } else {
                sb.append(constructor.getParameterCount()).append(" Parameters");
            }
            constructors.add(sb.toString());
        });

        return constructors;
    }

    public List<String> getParameterListAsText(int index) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < constructorList.get(index).getParameterCount(); i++) {
            list.add(constructorList.get(index).getParameterTypes()[i].getSimpleName());
        }
        return list;
    }

    public Constructor<?> getConstructorParameterTypes(int constructorIndex){
        return constructorList.get(constructorIndex);

//        return null;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getParentClass() {
        return parentClass;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<Class<?>> getChildrenList() {
        return childrenList;
    }

    ////Setters
    public void setParentClass(String parentClassName) {
        parentClass = parentClassName;
    }
}
