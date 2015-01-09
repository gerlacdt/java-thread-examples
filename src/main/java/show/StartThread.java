package show;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartThread {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StartThread.class);
	
	public static void main(String[] args) {
		
		// create new Thread
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				LOGGER.info("Hello from thread");
			}
		});
		t.start();
	}
}
