import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
 
public class CarInspector
{
    public static void main(String[] args)
    {
    	// try to set platform-specific "look and feel"
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) 
		{
				e.printStackTrace();
		}
        MainView mainFrame = new MainView("CarInspector");
        // make component visible
        mainFrame.setVisible(true);
    }
}