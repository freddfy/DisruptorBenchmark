package fred.utils;

/**
 * Author:  Fred Deng
 */
public class BenchmarkUtil {
    private long time;
    private Runnable setTime = new Runnable() {
        @Override
        public void run() {
            time = now();
        }
    };

    public long now() {
        return System.currentTimeMillis();
    }

    public long getTime(){
        return time;
    }


    public Runnable getRunnable() {
        return setTime;
    }
}
