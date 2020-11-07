package hay.harbor;

import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.LinkedList;


public class DockPool<T> {
	private final static int POOL_SIZE = 3; // количество причалов в порту
	private final Semaphore semaphore = new Semaphore(POOL_SIZE, true);
	private final Queue<Dock> terminals = new LinkedList<>();

	public DockPool(Queue<Dock> source) {
		terminals.addAll(source);
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
		terminals.add(term); // возврат в пул
		semaphore.release();
	}
}