
public class UpdateThread extends Thread {
	
	private MainView linkedMainView;
	
	private ParameterView paraView;	
	private ParameterViewMulti paraView2;
	private GraphVisualization graphVisu;
	private GraphVisualizationMulti graphVisu2;
	
	public UpdateThread(MainView mainView)
	{
		linkedMainView=mainView;
	}
	
		
	public void run()
	{
		while(true)
		{
			
			int activePanelCount = linkedMainView.getGraphPanel().getComponentCount();
			float maxPriority = 0;
			int maxPriorityID=0;
			if(activePanelCount>0)
			{
				if(!linkedMainView.isSingleView())
				{
					//iterate over graph-panels
					for(int i=0;i<activePanelCount;i++)
					{
						paraView = (ParameterView)linkedMainView.getGraphPanel().getComponent(i);
						float currentPriority = paraView.updateAndGetPriority();
						if(currentPriority>maxPriority)
						{
							maxPriority=currentPriority;
							maxPriorityID=i;
						}	        	
					}
					paraView = (ParameterView)linkedMainView.getGraphPanel().getComponent(maxPriorityID);
					graphVisu = (GraphVisualization)paraView.getVisualisation();	  
					paraView.resetPriority();
					
					//TODO: hier anfrage abschicken
					
					try { sleep(200); }
					catch(InterruptedException e) { }
					
					//TODO: hier wert abfragen
					
					float newValue = (float)Math.random();
					
					linkedMainView.logValue(paraView.getID(), newValue+"");
					
					graphVisu.addValue(newValue);
				}
				else //singleview
				{
					//System.out.println(">>"+activePanelCount+"");
					for(int i=1;i<activePanelCount;i++)
					{
						paraView2 = (ParameterViewMulti)linkedMainView.getGraphPanel().getComponent(i);
						float currentPriority = paraView2.updateAndGetPriority();						
						if(currentPriority>maxPriority)
						{
							maxPriority=currentPriority;
							maxPriorityID=i;
						}	        	
					}
					//System.out.println(maxPriorityID+"");
					if(maxPriority>0)
					{
						paraView2 = (ParameterViewMulti)linkedMainView.getGraphPanel().getComponent(maxPriorityID);
						graphVisu2 = (GraphVisualizationMulti)paraView2.getVisualisation();	  
						paraView2.resetPriority();
						
						//TODO: hier anfrage abschicken
						
						try { sleep(200); }
						catch(InterruptedException e) { }
						
						//TODO: hier wert abfragen
						
						float newValue = (float)Math.random();
						
						linkedMainView.logValue(paraView2.getID(), newValue+"");
						
						graphVisu2.addValue(newValue, maxPriorityID-1);
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
