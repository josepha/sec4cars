import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
 
public class CarInspector
{
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

        // create main window (currently empty)
        final JFrame mainFrame = new JFrame();
        mainFrame.setTitle("CarInspector");
        mainFrame.setSize(1024, 768);
 
        // create parameter view
        ParameterView view = new ParameterView("Sinus");
        // set size
        view.setSize(400,400);
        // set loaction
        view.setLocation(0,0);
        
        // add visualization
        // as a test this graph feeds itself with sin(systemTime)
        view.setVisualization(new ParameterGraph(3));
        
        // show
        mainFrame.setVisible(true);
        view.setVisible(true);
        
        // quit program when window is closed
        mainFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent windowevent ) {
                mainFrame.dispose();
                System.exit( 0 );
            }
        });

    }
}