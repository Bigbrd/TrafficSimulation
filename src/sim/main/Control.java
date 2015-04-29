package sim.main;

import sim.model.SimParameters;
import sim.model.SimulationFactory;
import sim.ui.UIMenuAction;
import sim.ui.UIError;
import sim.ui.UIFormBuilder;
import sim.ui.UI;
import sim.ui.UIForm;
import sim.ui.UIFormTest;
import sim.ui.UIMenu;
import sim.ui.UIMenuBuilder;

/**
 * Controls the user input actions and runs the simulation accordingly
 * 
 * @author rodri_000
 *
 */
class Control {
	private static enum STATE {
		EXITED, EXIT, START, EDIT_PARAM
	}

	private static final int NUM_STATES = STATE.values().length;
	private UIMenu[] _menus;
	private STATE _state;

	private UIForm _getRangeForm;
	private UIForm _getValueForm;
	private UIForm _getPatternForm;
	private UIForm _getGridForm;

	private UIFormTest _doubleTest;
	private UIFormTest _stringTest;
	private UIFormTest _intTest;

	private UI _ui;

	public Control(UI ui) {
		_ui = ui;
		_menus = new UIMenu[NUM_STATES];
		_state = STATE.START;
		addSTART(STATE.START);
		addEDITPARAM(STATE.EDIT_PARAM);
		addEXIT(STATE.EXIT);
		createFormTests();
		createForms();
	}

	private void createForms() {
		createRandomRangeForm();
		createValueForm();
		createPatternForm();
		createGridForm();
	}

	/**
	 * Creates the default form for the user to input the min and max of the
	 * ranges
	 */
	private void createRandomRangeForm() {
		UIFormBuilder f = new UIFormBuilder();
		f.add("Set minimum", _doubleTest);
		f.add("Set maximum", _doubleTest);
		_getRangeForm = f
				.toUIForm("Enter the min and max range for the simulation");

	}

	private void createValueForm() {
		UIFormBuilder f = new UIFormBuilder();
		f.add("Set value", _doubleTest);
		_getValueForm = f.toUIForm("Enter the value");
	}

	private void createPatternForm() {
		UIFormBuilder f = new UIFormBuilder();
		f.add("Enter true for alternating and false for the standard pattern",
				_stringTest);
		_getPatternForm = f.toUIForm("Alternating Pattern?");
	}

	private void createGridForm() {
		UIFormBuilder f = new UIFormBuilder();
		f.add("Enter the number of rows", _intTest);
		f.add("Enter the number of columns", _intTest);
		_getGridForm = f.toUIForm("Grid Simulation Size");
	}

	/**
	 * Establishes the tests used to validate the user input
	 */
	private void createFormTests() {
		_doubleTest = new UIFormTest() {
			public boolean run(String input) {
				try {
					Double.parseDouble(input);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		};

		_stringTest = new UIFormTest() {
			public boolean run(String input) {
				return !"".equals(input.trim());
			}
		};

		_intTest = new UIFormTest() {
			public boolean run(String input) {
				try {
					Integer.parseInt(input);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		};
	}

	/**
	 * Runs the UI
	 */
	void run() {
		try {
			while (_state != STATE.EXITED) {
				_ui.processMenu(_menus[_state.ordinal()]);
			}
		} catch (UIError e) {
			_ui.displayError("UI closed");
		}
	}

	/**
	 * Initial start state of UI
	 * 
	 * @param start
	 */
	private void addSTART(STATE start) {
		UIMenuBuilder m = new UIMenuBuilder();

		// Default menu action
		m.add("Default", new UIMenuAction() {
			public void run() {
				_ui.displayError("Doh!");
			}
		});

		// 1. Run Simulation action
		m.add("Run simulation", new UIMenuAction() {
			public void run() {
				SimulationFactory.newSimulation();
			}
		});

		// 2. Change Simulation Parameters action
		m.add("Change simulation parameters", new UIMenuAction() {
			public void run() {
				_state = STATE.EDIT_PARAM;
			}
		});

		m.add("Exit", new UIMenuAction() {
			public void run() {
				_state = STATE.EXIT;
			}
		});

		_menus[start.ordinal()] = m.toUIMenu("Traffic Simulator");
	}

	/**
	 * Edit parameter menu
	 * 
	 * @param editParam
	 */
	private void addEDITPARAM(STATE editParam) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() {
			public void run() {
			}
		});

		m.add("Show current values", new UIMenuAction() {
			public void run() {
				_ui.displayMessage(SimParameters.printParameters());
			}
		});

		m.add("Simulation time step", new UIMenuAction() {
			public void run() {
				String[] result = _ui.processForm(_getValueForm);
				SimParameters.setTimeStep(Double.parseDouble(result[0]));
			}
		});

		m.add("Simulation run time", new UIMenuAction() {
			public void run() {
				String[] result = _ui.processForm(_getValueForm);
				SimParameters.setRuntime(Double.parseDouble(result[0]));
			}
		});

		m.add("Grid size", new UIMenuAction() {
			public void run() {
				String[] result = _ui.processForm(_getGridForm);
				int[] dim = { Integer.parseInt(result[0]),
						Integer.parseInt(result[1]) };
				SimParameters.setDimension(dim);
			}
		});

		m.add("Traffic pattern", new UIMenuAction() {
			public void run() {
				String[] result = _ui.processForm(_getPatternForm);
				SimParameters.setPattern(result[0]);
			}
		});

		m.add("Car entry rate", new UIMenuAction() {
			public void run() {
				String[] result1 = _ui.processForm(_getRangeForm);
				double[] range = { Double.parseDouble(result1[0]),
						Double.parseDouble(result1[1]) };
				SimParameters.setCarGenDelay(range);
			}
		});

		m.add("Road segment length", new UIMenuAction() {
			public void run() {
				String[] result1 = _ui.processForm(_getRangeForm);
				double[] range = { Double.parseDouble(result1[0]),
						Double.parseDouble(result1[1]) };
				SimParameters.setRoadLength(range);
			}
		});

		m.add("Intersection length", new UIMenuAction() {
			public void run() {
				String[] result1 = _ui.processForm(_getRangeForm);
				double[] range = { Double.parseDouble(result1[0]),
						Double.parseDouble(result1[1]) };
				SimParameters.setIntersectionLength(range);
			}
		});

		m.add("Car length", new UIMenuAction() {
			public void run() {
				String[] result1 = _ui.processForm(_getRangeForm);
				double[] range = { Double.parseDouble(result1[0]),
						Double.parseDouble(result1[1]) };
				SimParameters.setCarLength(range);
			}
		});

		m.add("Car maximum velocity", new UIMenuAction() {
			public void run() {
				String[] result1 = _ui.processForm(_getRangeForm);
				double[] range = { Double.parseDouble(result1[0]),
						Double.parseDouble(result1[1]) };
				SimParameters.setCarMaxVelocity(range);
			}
		});

		m.add("Car stop distance", new UIMenuAction() {
			public void run() {
				String[] result1 = _ui.processForm(_getRangeForm);
				double[] range = { Double.parseDouble(result1[0]),
						Double.parseDouble(result1[1]) };
				SimParameters.setCarStopDistance(range);
			}
		});

		m.add("Car brake distance", new UIMenuAction() {
			public void run() {
				String[] result1 = _ui.processForm(_getRangeForm);
				double[] range = { Double.parseDouble(result1[0]),
						Double.parseDouble(result1[1]) };
				SimParameters.setCarBrakeDistance(range);
			}
		});

		m.add("Traffic light green time", new UIMenuAction() {
			public void run() {
				String[] result1 = _ui.processForm(_getRangeForm);
				double[] range = { Double.parseDouble(result1[0]),
						Double.parseDouble(result1[1]) };
				SimParameters.setTrafficGreenTime(range);
			}
		});

		m.add("Traffic light yellow time", new UIMenuAction() {
			public void run() {
				String[] result1 = _ui.processForm(_getRangeForm);
				double[] range = { Double.parseDouble(result1[0]),
						Double.parseDouble(result1[1]) };
				SimParameters.setTrafficYellowTime(range);
			}
		});

		m.add("Return to the main menu", new UIMenuAction() {
			public void run() {
				_state = STATE.START;
			}
		});

		m.add("Reset simulation and return to the main menu",
				new UIMenuAction() {
					public void run() {
						SimParameters.resetParameters();
						_state = STATE.START;
					}
				});

		_menus[editParam.ordinal()] = m.toUIMenu("Traffic Parameters");

	}

	/**
	 * Exiting menu
	 * 
	 * @param exit
	 */
	private void addEXIT(STATE exit) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() {
			public void run() {
			}
		});
		m.add("Yes", new UIMenuAction() {
			public void run() {
				_state = STATE.EXITED;
			}
		});
		m.add("No", new UIMenuAction() {
			public void run() {
				_state = STATE.START;
			}
		});

		_menus[exit.ordinal()] = m.toUIMenu("Are you sure you want to exit?");
	}
}
