import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
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