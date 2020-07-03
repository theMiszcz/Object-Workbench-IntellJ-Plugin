package ac.uk.teamWorkbench.workbenchRuntime;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton as we expect only one execution loop.
 */
public class ExecutionLoop implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ExecutionLoop.class.getName());

    private static ExecutionLoop instance = null;

    private boolean isRunning = false;
    private ObjectCreator objectCreator;

    private ExecutionLoop() {
        startLoop();
    }

    public static ExecutionLoop getInstance() {
        if (instance == null) instance = new ExecutionLoop();
        return instance;
    }

    @Override
    public void run() {
        //startLoop();
    }

    private void startLoop() {
        objectCreator = new ObjectCreator();
        isRunning = true;

        LOGGER.log(Level.INFO, "Starting Execution Loop");
        /* while (isRunning) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Failed to start Execution Loop" + e.getMessage());
            }
        } */
        LOGGER.log(Level.INFO, "Execution Loop Finished");
    }

    public boolean instantiateObject(String className, int chosenConstructor, Object[] arguments) {
        if (isRunning)
            return objectCreator.instantiateObject(className, chosenConstructor, arguments);
        else{
            LOGGER.log(Level.INFO, "Execution Loop is not instantiated!");
            return false;
        }
    }

    public Object getObject(int index){
        return objectCreator.getObject(index);
    }

    public void removeObject(int index){
        objectCreator.removeObject(index);
    }

    public void removeAllObjects(){
        objectCreator.removeAllObjects();
    }

    public void updateObject(Object object, int index){
        objectCreator.updateObject(object, index);
    }

    public  Class<?>[] getParamTypeList(){
        return objectCreator.getParamTypes();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public Object getObjectType(Class<?> clazz, String value){
        try {
            return objectCreator.getObjectType(clazz, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
