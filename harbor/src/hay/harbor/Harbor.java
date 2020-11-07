package hay.harbor;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Harbor {
	private int capacity;
	private volatile int current;

	private final Semaphore terminal = new Semaphore(1, true);

	public Harbor(int capacity, int current) {
		this.capacity = capacity;
		this.current = current;
	}

	public boolean received() {
		try {
			if (terminal.tryAcquire(500, TimeUnit.MILLISECONDS)) {
				if (current < capacity) {
					current++;
					System.out.println("Container was receiveng, amount: " + current);
					return true;
				}
				else
					return false;
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		} finally {
			terminal.release();
		}
		return false;
	}

	public boolean sent() {
		try {
			if (terminal.tryAcquire(500, TimeUnit.MILLISECONDS)) {
				if (current > 0) {
					current--;
					System.out.println("Container was sending, amount: " + current);
					return true;
				}
				else
					return false;
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		} finally {
			terminal.release();
		}
		return false;
	}

}
