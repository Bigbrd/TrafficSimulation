package sim.ui;

/**
 * Factory that creates a new Popup UI
 * 
 * @author rodri_000
 *
 */
public class UIFactory {
	private UIFactory() {
	}

	static private UI _UI = new PopupUI();

	static public UI ui() {
		return _UI;
	}

}
