

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
		
        MainView mainView = new MainView("CarInspector");
        // make component visible
        mainView.setVisible(true); 
        
        

       final UpdateThread getData = new UpdateThread(mainView);
       getData.start();
        
//        mainView.addComponentListener(new ComponentAdapter() {
//            @SuppressWarnings("deprecation")
//			@Override
//            public void componentHidden(ComponentEvent e) {
//            	getData.stop();
//            	getData.destroy();
//            	((MainView)(e.getComponent())).shutDown();
//                ((MainView)(e.getComponent())).dispose();
//            }
//        });

    }
}