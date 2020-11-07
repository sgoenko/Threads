package hay.harbor;

import java.util.LinkedList;

public class HarborRunner {
	public static void main(String[] args) {
		Harbor harbor = new Harbor(50, 20);
		
		LinkedList<Dock> terminals = new LinkedList<>() {
			{
				this.add(new Dock(1, harbor));
				this.add(new Dock(2, harbor));
				this.add(new Dock(3, harbor));
			}
		};
		
		DockPool<Dock> pool = new DockPool<>(terminals);

		Ship Mary = new Ship(pool, "<Mary>", 10, 10);
		
		Ship Calipso = new Ship(pool, "<Calipso>", 10, 5);
		Calipso.setAction(Action.LOAD);
		
		Ship Guron = new Ship(pool, "<Guron>", 10, 0);
		Guron.setAction(Action.LOAD);
		
		Ship Star = new Ship(pool, "<Star>", 12, 10);
		
		Ship Victory = new Ship(pool, "<Victory>", 15, 15);
		
		Mary.start();
		Calipso.start();
		Guron.start();
		Star.start();
		Victory.start();
	}
}
