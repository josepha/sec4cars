
public class UpdateThread extends Thread {
	
	private MainView linkedMainView;
	
	private AbstractView paraView;	
	//private ParameterViewMulti paraView2;
	private AbstractVisualization graphVisu;
	//private GraphVisualizationMulti graphVisu2;
	
	private float currentPriority;
	private int activePanelCount;
	private float maxPriority;
	private int maxPriorityID;
	
	public UpdateThread(MainView mainView)
	{
		linkedMainView=mainView;
	}
	
		
	public void run()
	{
		while(true)
		{
			
			activePanelCount = linkedMainView.getGraphPanel().getComponentCount();
			maxPriority = 0;
			maxPriorityID = 0;
			if(activePanelCount>0 && linkedMainView.viewReady())
			{								
				if(!linkedMainView.isSingleView())
				{
					//iterate over graph-panels
					for(int i=0;i<activePanelCount;i++)
					{
						paraView = (AbstractView)linkedMainView.getGraphPanel().getComponent(i);
						currentPriority = paraView.updateAndGetPriority();
						if(currentPriority>maxPriority)
						{
							maxPriority=currentPriority;
							maxPriorityID=i;
						}	        	
					}
					paraView = (AbstractView)linkedMainView.getGraphPanel().getComponent(maxPriorityID);
					graphVisu = (AbstractVisualization)paraView.getVisualisation();	  
					paraView.resetPriority();
					
					//TODO: hier anfrage abschicken
					
					try { sleep(200); }
					catch(InterruptedException e) { }
					
					//TODO: hier wert abfragen
					
					float newValue = (float)Math.random();
					
					linkedMainView.logValue(paraView.getID(), newValue+"");
					
					graphVisu.addValue(newValue,0);
				}
				else //singleview
				{
					
					for(int i=1;i<activePanelCount;i++)
					{
						paraView = (AbstractView)linkedMainView.getGraphPanel().getComponent(i);
						currentPriority = paraView.updateAndGetPriority();						
						if(currentPriority>maxPriority)
						{
							maxPriority=currentPriority;
							maxPriorityID=i;
						}	        	
					}
					//System.out.println(maxPriorityID+"");
					if(maxPriority>0)
					{
						paraView = (AbstractView)linkedMainView.getGraphPanel().getComponent(maxPriorityID);
						graphVisu = (AbstractVisualization)paraView.getVisualisation();	  
						paraView.resetPriority();
						
						//TODO: hier anfrage abschicken
						
						try { sleep(200); }
						catch(InterruptedException e) { }
						
						//TODO: hier wert abfragen
						
						float newValue = (float)Math.random();
						
						linkedMainView.logValue(paraView.getID(), newValue+"");
						
						graphVisu.addValue(newValue, maxPriorityID-1);
					}
					else
					{
						try { sleep(200); }
						catch(InterruptedException e) { }
					}					
				}								
			}	
		}
	}
}
