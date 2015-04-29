package sim.model;

import junit.framework.TestCase;

public class RoadTEST extends TestCase {
	public RoadTEST(String name) {
		super(name);
	}

	public void testRoadCreation() {
		Road r = new Road(null);
		assertTrue(r.getLength() > 200.0 && r.getLength() < 500.0);
	}

	public void testNext() {
		Road r2 = new Road(null);
		Road r1 = new Road(r2);
		assertTrue(r1.getNext() != null);
	}

	public void testDistanceToObstacle() {
		Road r1 = new Road(null);
		r1.setLength(200.0);
		assertEquals(20.0, r1.distanceToObstacle(180));
		assertEquals(0.0, r1.distanceToObstacle(200.0));
		
		Car c = CarFactory.createCar();
		r1.accept(c, c.getFrontPosition());
		int i = 0;
		while(i < 10){
			c.run();
			i++;
		}
		assertEquals(c.getBackPosition(), r1.distanceToObstacle(0.0));
	}

	public void testAccept() {
		Car c = CarFactory.createCar();
		Road r1 = new Road(null);
		r1.setLength(100.0);
		assertTrue(r1.accept(c, c.getFrontPosition()));
		c.setFrontPosition(150.0);
		assertFalse(r1.accept(c, c.getFrontPosition()));
	}
}
