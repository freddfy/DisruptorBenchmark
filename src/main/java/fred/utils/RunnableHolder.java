package fred.utils;

/**
 * Author:  Fred Deng
 */
public class RunnableHolder {

    private Runnable runnable;

    public Runnable getRunnable(){
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}
