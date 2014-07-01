package fred.utils;

import com.lmax.disruptor.EventHandler;

/**
 * Author:  Fred Deng
 */
public class RunnableHandler implements EventHandler<RunnableHolder> {

    @Override
    public void onEvent(RunnableHolder event, long sequence, boolean endOfBatch) throws Exception {
         event.getRunnable().run();
    }
}
