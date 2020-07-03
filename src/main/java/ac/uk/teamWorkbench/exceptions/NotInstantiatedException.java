package ac.uk.teamWorkbench.exceptions;

/**
 * Exception called when a singleton object has not been instantiated at least once.
 */
public class NotInstantiatedException extends RuntimeException {

    public NotInstantiatedException(){
        super("This Object needs to be instantiated at least once." +
                "\nUse instantiateObject() method.");
    }

    public NotInstantiatedException(String string){
        super(string);
    }
}
