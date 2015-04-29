package sim.model;

public class SimulationFactory {
	private static Simulation _sim;

	private SimulationFactory() {
	}

	static public void newSimulation() {
		_sim = new Simulation();
		_sim.run();
		_sim.dispose();
	}
}
