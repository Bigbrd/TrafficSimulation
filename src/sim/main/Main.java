package sim.main;

import sim.ui.PopupUI;
import sim.ui.UI;

/**
 * Runs the UI to run and control the simulation
 * 
 * @author rodri_000
 *
 */
public class Main {

	public static void main(String[] args) {
		UI ui = new PopupUI();
		Control control = new Control(ui);
		control.run();
	}

}
