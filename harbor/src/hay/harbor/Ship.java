package hay.harbor;

public class Ship extends Thread {
	private Harbor harbor;
	private String name;
	private int capacity;
	private int current;
	private ServiceType serviceType;
	private boolean needService = false;

	public Ship(Harbor harbor, String name, int capacity, int current) {
		this.harbor = harbor;
		this.name = name;
		this.capacity = capacity;
		this.current = current;
		serviceType = ServiceType.UPLOAD;
		needService = true;
	}

	public void setAction(ServiceType action) {
		this.serviceType = action;
	}

	public void run() {
		while (needService) {
			service();
		}
	}

	private void service() {
		Dock dock = occupyDock();

		if (dock != null) {
			System.out.println(name + " occupied dock #" + dock.getId());
			docking(dock);
			System.out.println(name + " released dock #" + dock.getId());
			harbor.releaseDock(dock);
			if (serviceFinished()) {
				needService = false;
			}
		} else {
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(name + ": no docks available");
		}
	}

	private void docking(Dock dock) {
		if (serviceType == ServiceType.LOAD) {
			while (current < capacity) {
				if (dock.load()) {
					load();
				} else {
					break;
				}
			}
		} else {
			while (current > 0) {
				if (dock.upload()) {
					upload();
				} else {
					break;
				}
			}
		}
	}
	
	private Dock occupyDock() {
		if (serviceType == ServiceType.LOAD)
			return harbor.occupyDockForLoad(500, capacity - current);
		else
			return harbor.occupyDockForUpload(500, current);
	}

	private boolean serviceFinished() {
		if (serviceType == ServiceType.LOAD)
			return current == capacity;
		else
			return current == 0;
	}

	public void load() {
		current++;
		System.out.println(serviceType + " " + name + "+1. Total: " + current);
	}

	public void upload() {
		current--;
		System.out.println(serviceType + " " + name + "-1. Total: " + current);
	}

	public int getCapacity() {
		return capacity;
	}

	public int getCurrent() {
		return current;
	}

}
