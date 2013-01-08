// abstract slotview class for CarInspector
// all views must extend this type

import javax.swing.JPanel;

public abstract class AbstractView extends JPanel
{
	private static final long serialVersionUID = -309946510270639470L;
	
	// update method called by contained graph modules
	public abstract void updateRangeFields();
	
	public AbstractView()
	{
		// call JPanel constructor
		super();
	}
	
}
