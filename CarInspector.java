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
     
        
        // update loop
//        int activePanelCount = mainView.getGraphPanel().getComponentCount();
//        for(int i=0;i<activePanelCount;i++)
//        {
//        	ParameterView paraView = (ParameterView)mainView.getGraphPanel().getComponent(i);
//        	paraView.graphVisu.update(0.5, 0.01);
//        }
        
        UpdateThread getData = new UpdateThread(mainView);
        getData.start();

    }
}