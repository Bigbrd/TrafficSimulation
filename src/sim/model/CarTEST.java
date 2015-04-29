package sim.model;

import junit.framework.TestCase;

public class CarTEST extends TestCase {
	public CarTEST(String name) {
		super(name);
	}

	public void testGettersAndSetters() {
		Car c = CarFactory.createCar();
		c.setFrontPosition(50.0);
		assertEquals(50.0, c.getFrontPosition());
	}

	public void testRun() {
		Car c = CarFactory.createCar();
		Road r = new Road(new Sink());
		assertEquals(0.0, c.getFrontPosition());
		assertTrue(r.accept(c, c.getFrontPosition()));
		int i = 0;
		while (i < 10) {
			double position = c.getFrontPosition();
			c.run();
			assertTrue(c.getFrontPosition() > position);
			i++;
		}
		c.run();
	}
}
