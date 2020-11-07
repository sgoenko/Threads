package hay.harbor;

public class Ship extends Thread {
	private boolean active = false;
	private DockPool<Dock> pool;
	private String name;
	private int capacity;
	private int current;
	private Action action;

	public Ship(DockPool<Dock> pool, String name, int capacity, int current) {
		this.pool = pool;
		this.name = name;
		setCapacity(capacity);
		setCurrent(current);
		action = Action.UPLOAD;
		active = true;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void run() {
		Dock dock = null;
		while (active) {
			try {
				dock = pool.occupyDock(500);
				System.out.println(name + " occupied dock #" + dock.getId());
				dock.setShip(this);
				if (action == Action.LOAD) {
					while (current < capacity)
						dock.loading();
				}
				if (action == Action.UPLOAD) {
					while (current > 0)
						dock.uploading();
				}
				active = false;
			} catch (DockException e) {
				System.out.println(name + " >> " + e.getMessage());
			} finally {
				if (dock != null) {
					active = false;
					System.out.println(name + " : " + dock.getId() + " dock released");
					pool.releaseDock(dock);
				}
			}
		}
	}

	public void load() {
		current++;
		System.out.println(action + " " + name + "+1. Total: " + current);
	}

	public void upload() {
		current--;
		System.out.println(action + " " + name + "-1. Total: " + current);
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}
}
