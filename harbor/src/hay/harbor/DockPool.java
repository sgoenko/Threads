package hay.harbor;

import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.LinkedList;


public class DockPool<T> {
	private final Semaphore semaphore;
	private final Queue<Dock> terminals = new LinkedList<>();

	public DockPool(Queue<Dock> source) {
		terminals.addAll(source);
		semaphore = new Semaphore(terminals.size(), true);
	}

	public Dock occupyDock(long maxWaitMillis) throws DockException {
		try {
			if (semaphore.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
				return terminals.poll();
			}
		} catch (InterruptedException e) {
			throw new DockException(e);
		}
		throw new DockException(":превышено время ожидания");
	}

	public void releaseDock(Dock term) {
		terminals.add(term); 
		semaphore.release();
	}
}