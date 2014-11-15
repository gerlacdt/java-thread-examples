package show;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copied form Java Tutorials:
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html
 */
public class Deadlock {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Deadlock.class);

	static class Friend {
		private final String name;

		public Friend(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public synchronized void bow(Friend bower) {
			System.out.format("%s: %s" + "  has bowed to me!%n", this.name,
					bower.getName());
			bower.bowBack(this);
		}

		public synchronized void bowBack(Friend bower) {
			System.out.format("%s: %s" + " has bowed back to me!%n", this.name,
					bower.getName());
		}
	}

	public static void main(String[] args) {
		final Friend alphonse = new Friend("Alphonse");
		final Friend gaston = new Friend("Gaston");
		new Thread(new Runnable() {
			public void run() {
				alphonse.bow(gaston);
			}
		}).start();
		
		try {
			// change time to provoke DEADLOCK
			Thread.sleep(0);
		} catch (InterruptedException e) {
			LOGGER.error("interrupted during sleep: ", e);
		}
		
		new Thread(new Runnable() {
			public void run() {
				gaston.bow(alphonse);
			}
		}).start();
	}

}
