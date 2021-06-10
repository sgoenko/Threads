package hay.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Runner {

	public void run() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		ReentrantLock lock = new ReentrantLock();

		executor.submit(() -> {
			lock.lock();
			try {
				ConcurrentUtils.sleep(1);
			} finally {
				lock.unlock();
			}
		});

		executor.submit(() -> {
			System.out.println("Locked: " + lock.isLocked());
			System.out.println("Held by me: " + lock.isHeldByCurrentThread());
			boolean locked = lock.tryLock();
			System.out.println("Lock acquired: " + locked);
		});

		ConcurrentUtils.stop(executor);
	}

	public static void main(String[] args) {
		Runner runner = new Runner();
		try {
			runner.run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
