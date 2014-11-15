package show;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoVisibility extends Thread {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NoVisibility.class);

    // shared variable (between threads)
    static boolean keepRunning = true;

    public static void main(String[] args) throws InterruptedException {
    	NoVisibility t = new NoVisibility();
        for (int i = 0; i < 1; i++) {
        	t.start();
        }
        Thread.sleep(1000);
        keepRunning = false;
        LOGGER.info(System.currentTimeMillis() + ": keepRunning is false");
        t.join();
    }

    public void run() {
        while (keepRunning) {
        	// I/O is too slow for showcase
        	// LOGGER.info(System.currentTimeMillis() + ": " + keepRunning);
        }
    }
}
