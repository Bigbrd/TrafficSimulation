package sim.model;

import junit.framework.TestCase;
import sim.model.LightController;
import sim.model.Light.FlowIndicator;
import sim.model.LightController.Direction;

public class LightTEST extends TestCase {
	public LightTEST(String name) {
		super(name);
	}

	public void testGettersAndSetters() {
		Road r1 = new Road(null);
		Light l = new Light(r1);
		l.setState(FlowIndicator.GO);
		assertEquals(FlowIndicator.GO, l.getFlow());
		l.setState(FlowIndicator.CAUTION);
		assertEquals(FlowIndicator.CAUTION, l.getFlow());
		l.setState(FlowIndicator.STOP);
		assertEquals(FlowIndicator.STOP, l.getFlow());
	}

	public void testDistanceToObstacle() {
		Road r1 = new Road(null);
		r1.setLength(200);
		Light l = new Light(r1);
		l.setState(FlowIndicator.STOP);
		assertEquals(0.0, l.distanceToObstacle(200));
		l.setState(FlowIndicator.GO);
		assertEquals(200.0, l.distanceToObstacle(0));
		l.setState(FlowIndicator.CAUTION);
		assertEquals(50.0, l.distanceToObstacle(150));
	}

	public void testAccept() {
		Road r1 = new Road(null);
		r1.setLength(100);
		Car c = CarFactory.createCar();
		Light l1 = new Light(r1);
		l1.setState(FlowIndicator.STOP);
		assertFalse(l1.accept(c, 0));
		l1.setState(FlowIndicator.GO);
		assertTrue(l1.accept(c, 0));
	}

	public void testLightController() {
		Light lew = new Light(null);
		Light lns = new Light(null);
		LightController lc = new LightController();
		lc.setLight(lew, Direction.EASTWEST);
		lc.setLight(lns, Direction.NORTHSOUTH);

		int i = 0;
		while (i < 500) {
			lc.run();
			if (lc.getEastWestLight().getFlow() == FlowIndicator.GO
					&& lc.getNorthSouthLight().getFlow() == FlowIndicator.GO)
				fail();
			else if (lc.getEastWestLight().getFlow() == FlowIndicator.STOP
					&& lc.getNorthSouthLight().getFlow() == FlowIndicator.STOP)
				fail();
			i++;
		}

	}

}
