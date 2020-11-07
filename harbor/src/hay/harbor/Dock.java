package hay.harbor;

public class Dock {
	private int id;
	private Harbor harbor;
	private Ship ship;

	public Dock(int id, Harbor harbor) {
		this.id = id;
		this.harbor = harbor;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public int getId() {
		return id;

	}

	public void loading() {
		try {
			if (ship.getCurrent() < ship.getCapacity()) {
				if (harbor.sent())
					ship.load();
			}
			Thread.sleep(new java.util.Random().nextInt(50));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void uploading() {
		try {
			if (ship.getCurrent() > 0) {
				if (harbor.received())
					ship.upload();
			}
			Thread.sleep(new java.util.Random().nextInt(50));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}