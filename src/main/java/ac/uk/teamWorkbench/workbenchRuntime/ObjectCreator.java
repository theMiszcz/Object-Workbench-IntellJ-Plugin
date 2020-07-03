package ac.uk.teamWorkbench.workbenchRuntime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObjectCreator {

    private static final Logger LOGGER = Logger.getLogger(ObjectCreator.class.getName());

    private List<Object> loadedObjects;

    private Class<?>[] paramTypes;

    public ObjectCreator(){
        loadedObjects = new ArrayList<>();
    }

    public Object createObject(){

        return null;
    }

    //TODO add bunch of type checking for what is not allowed to be initalized
    private boolean isCorrectType(Class<?> clazz){
        int classModifier = clazz.getModifiers();

        if(Modifier.isAbstract(classModifier)){
            LOGGER.log(Level.INFO, "Class is type Abstract, cannot be initialized.");
            return false;
        }
        if(Modifier.isInterface(classModifier)){
            LOGGER.log(Level.INFO, "Class is type Interface, cannot be initialized.");
            return false;
        }


        return true;
    }


    boolean instantiateObject(String className, int chosenConstructor, Object[] arguments) {
        Map<String, ClassReflection> classReflectionMap = ObjectPool.getInstance().getClassReflectionMap();
        ClassReflection classReflection = classReflectionMap.get(className);

        Class<?> clazz = classReflection.getClazz();

        if(isCorrectType(clazz)) {
            Constructor<?> x = clazz.getDeclaredConstructors()[chosenConstructor];
            Class<?>[] parameterTypes = x.getParameterTypes();

            setParamTypes(parameterTypes);

            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                try {
                    parameters[i] = toObject(parameterTypes[i], arguments[i].toString());
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Failed to extract constructor's parameters: " + e.getMessage());
                    return false;
                }
            }

            Object newObject = null;
            try {
                newObject = x.newInstance(parameters);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to instantiate the object: " + e.getMessage());
                return false;
            }
            loadedObjects.add(newObject);
            LOGGER.log(Level.INFO, "Successfully instantiated an object: " + newObject.getClass());
            return true;
        }
        return false;
    }

    public Object getObjectType(Class<?> clazz, String value) throws Exception{
        return toObject(clazz, value);

    }

    private Object toObject(Class<?> clazz, String value) throws Exception {
        if (clazz.isPrimitive()) return toPrimitive(clazz, value);

        if (Boolean.class == clazz) return Boolean.parseBoolean(value);
        if (Byte.class == clazz) return Byte.parseByte(value);
        if (Short.class == clazz) return Short.parseShort(value);
        if (Integer.class == clazz) return Integer.parseInt(value);
        if (Long.class == clazz) return Long.parseLong(value);
        if (Float.class == clazz) return Float.parseFloat(value);
        if (Double.class == clazz) return Double.parseDouble(value);
        return value;
    }

    private Object toPrimitive(Class<?> clazz, String value) throws Exception {
        if (clazz == Boolean.TYPE) return Boolean.parseBoolean(value);
        if (clazz == Byte.TYPE) return Byte.parseByte(value);
        if (clazz == Short.TYPE) return Short.parseShort(value);
        if (clazz == Integer.TYPE) return Integer.parseInt(value);
        if (clazz == Long.TYPE) return Long.parseLong(value);
        if (clazz == Float.TYPE) return Float.parseFloat(value);
        if (clazz == Double.TYPE) return Double.parseDouble(value);

        throw new Exception("Casting to primitive cast should never fail as it happens after checking if element is primitive");
    }

    /* Sets a list of parameter types expected by the object's constructor */
    private void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    /* Gets a list of parameter types expected by the object's constructor */
    public Class<?>[] getParamTypes(){
        return paramTypes;
    }

    public Object getObject(int index){
        return loadedObjects.get(index);
    }

    public void removeObject(int index){
        loadedObjects.remove(index);
    }
    public void removeAllObjects(){
        loadedObjects.clear();
    }
    public void updateObject(Object object, int index){
        if(!loadedObjects.isEmpty()) {
            loadedObjects.remove(index);
            loadedObjects.add(index, object);
        }

    }

}
