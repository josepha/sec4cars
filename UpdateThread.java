
public class UpdateThread extends Thread {
	
	MainView linkedMainView;
	
	public UpdateThread(MainView mainView)
	{
		linkedMainView=mainView;
	}
	
	public void run() {
	      while(true) {
	        try {
	          sleep(200);
	        }
	        catch(InterruptedException e) {
	        }	        
	        int activePanelCount = linkedMainView.getGraphPanel().getComponentCount();
	        float maxPriority = 0;
	        int maxPriorityID=0;
	        if(activePanelCount>0)
	        {
	        	for(int i=0;i<activePanelCount;i++)
	        	{
	        		ParameterView paraView = (ParameterView)linkedMainView.getGraphPanel().getComponent(i);
	        		float currentPriority = paraView.updateAndGetPriority();
	        		if(currentPriority>maxPriority)
	        		{
	        			maxPriority=currentPriority;
	        			maxPriorityID=i;
	        		}	        	
	        	}
	        	ParameterView paraViewToUpdate = (ParameterView)linkedMainView.getGraphPanel().getComponent(maxPriorityID);
	        	GraphVisualization visu = (GraphVisualization)paraViewToUpdate.getVisualisation();	        	
	        	visu.addValue((float)Math.random());
	        	paraViewToUpdate.resetPriority();
	        }
	      }
	}
}
