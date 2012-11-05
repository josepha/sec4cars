// Import-Anweisungen
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
 
public class CarInspector
{
	
    // main-Methode
    public static void main(String[] args)
    {
    	// try to set platform-specific "look and feel"
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // create dialog
        final JFrame mainFrame = new JFrame();
        mainFrame.setTitle("CarInspector");
        mainFrame.setSize(1024, 768);
 
        // create JDesktopPane
        JDesktopPane deskPane = new JDesktopPane();
 
        // set background color to gray
        deskPane.setBackground(Color.GRAY);
 
        //create parameter view
        ParameterView view = new ParameterView("Parameter 1");
        
        view.setVisualization(new ParameterGraph("Parameter 1", -1, -1, 30));
        
        // set size
        view.setSize(400,400);
        // set relative loaction
        view.setLocation(0,0);
        //JInternalFrames werden sichtbar gemacht
        //add pane to dialog
        mainFrame.add(deskPane);
        //view dialog
        mainFrame.setVisible(true);
        view.setVisible(true);
        
        mainFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent windowevent ) {
                mainFrame.dispose();
                System.exit( 0 );
            }
        });

    }
}