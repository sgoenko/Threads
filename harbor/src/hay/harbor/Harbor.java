package hay.harbor;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Harbor {
	private int capacity;
	private volatile int current;
	private DockPool<Dock> pool;		

	private final Semaphore terminal = new Semaphore(1, true);

	public Harbor(int capacity, int current, int dockNumber) {
		this.capacity = capacity;
		this.current = current;
		
		LinkedList<Dock> terminals = new LinkedList<>();
		for (int i=1; i <= dockNumber; i++) {
			terminals.add(new Dock(i, this));
		}
			
		pool = new DockPool<>(terminals);
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

	public DockPool<Dock> getPool() {
		return pool;
	}

	public void setPool(DockPool<Dock> pool) {
		this.pool = pool;
	}

	public void releaseDock(Dock dock) {
		pool.releaseDock(dock);
		
	}

	public Dock occupyDockForLoad(long maxWaitMillis, int volume) {
		if (current >= volume)
			try {
				return pool.occupyDock(maxWaitMillis);
			} catch (DockException e) {
				System.out.println("No docks are available...");;
			}
		return null;
	}

	public Dock occupyDockForUpload(long maxWaitMillis, int volume) {
		if ((capacity - current) >= volume)
			try {
				return pool.occupyDock(maxWaitMillis);
			} catch (DockException e) {
				System.out.println("No docks are available...");;
			}
		return null;
	}

}
