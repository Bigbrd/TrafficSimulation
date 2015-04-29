package sim.ui;

/**
 * Interface that establishes what the UI should incorporate whether it be Popup
 * or text based
 * 
 * @author rodri_000
 *
 */
public interface UI {
	public void processMenu(UIMenu menu);

	public String[] processForm(UIForm form);

	public void displayMessage(String message);

	public void displayError(String message);
}
