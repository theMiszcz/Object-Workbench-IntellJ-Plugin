package ac.uk.teamWorkbench.objectWorkbench;

public abstract class ControllerTemplate {

    public abstract void init();
    public abstract void addListeners();

    public void start(){
        init();
        addListeners();
    }

}
