// container class for the visualizations
// will contain UI components to control output behavior 
// TODO: extend

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


import com.jogamp.opengl.util.FPSAnimator;

// TODO implement stuff belonging to the view
public class ParameterViewMultiGraphPanel extends AbstractView
{
	private static final long serialVersionUID = 1L;
	
	// visualization for this data block view
	private AbstractVisualization linkedVisualization = null;
	
    //*********************************************************************
	
	private Timer timer;
	
	// set up GUI components
	
	// canvas for openGL rendering
	private GLJPanel glPanel;


	// graph timeframe width buttons
	private JButton narrowerButton;
	private JButton broaderButton;
	private JLayeredPane layeredPane;
	private JPanel buttonPanel;
	

	
	//private String ID;
	
	public void setVisualization(AbstractVisualization visualization)
	{		
		glPanel.addGLEventListener(visualization);
		linkedVisualization = visualization;
		visualization.setContainingView(this);
		updateRangeFields();
//		GraphColor col = linkedVisualization.getGraphColor();
//		// could not be returned by the view
//		if(col != null) colorLabel.setBackground(col.toJColor());
//		// show that color of this visualisation ist not editable
//		else colorLabel.setOpaque(false);
	}
	
	public AbstractVisualization getVisualisation()	{
		return linkedVisualization;
	}
	
	// helper for updating textfields holding color range
	public void updateRangeFields()
	{
//		if(linkedVisualization == null) return;
//		minVal.setText( String.format("%.3f", linkedVisualization.getMinValue()) );
//		maxVal.setText( String.format("%.3f", linkedVisualization.getMaxValue()) );
	}

	public ParameterViewMultiGraphPanel() 
	{
		// parameter view setup
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		

		int buttonSize = 28;

		// set up time frame buttons
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
		ImageIcon broader = new ImageIcon("broader.png");
		ImageIcon narrower = new ImageIcon("narrower.png");
		broaderButton = new JButton(broader);
		broaderButton.setMinimumSize(new Dimension(2 *buttonSize,buttonSize));
		broaderButton.setPreferredSize(new Dimension(2 * buttonSize, buttonSize));
		broaderButton.setMaximumSize(new Dimension(2 * buttonSize, buttonSize));
		narrowerButton = new JButton(narrower);
		narrowerButton.setMinimumSize(new Dimension(2 * buttonSize,buttonSize));
		narrowerButton.setPreferredSize(new Dimension(2 * buttonSize, buttonSize));
		narrowerButton.setMaximumSize(new Dimension(2 * buttonSize, buttonSize));
		buttonPanel.add(broaderButton);
		buttonPanel.add(narrowerButton);
		
		// set up openGL graph panel
		GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        glPanel = new GLJPanel( glcapabilities );
        glPanel.setMinimumSize(new Dimension(50,500));
        glPanel.setPreferredSize(new Dimension(400,500));
        glPanel.setMaximumSize(new Dimension(1500,500));
 
        
        // TODO: remove later on
        FPSAnimator animator = new FPSAnimator(glPanel, 60);
        animator.add(glPanel); //???
        animator.start();
        add(Box.createHorizontalGlue());
           

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new BoxLayout(layeredPane, BoxLayout.PAGE_AXIS));
        //com.sun.lwuit.layouts.Layout
        layeredPane.setAlignmentX(LEFT_ALIGNMENT);
        layeredPane.add(glPanel);
        glPanel.setAlignmentX(Box.LEFT_ALIGNMENT);
        layeredPane.setMinimumSize(new Dimension(50,550));
        layeredPane.setPreferredSize(new Dimension(400,550));
        layeredPane.setMaximumSize(new Dimension(1500,550));
        layeredPane.add(buttonPanel);
        add(layeredPane);

        //add(Box.createHorizontalGlue());
        //add(buttonPanel);
        //add(Box.createVerticalStrut(5));
        //add(new JSeparator());
        
        //*********************************************************************
        
        // needs to be passed to buttonListeners
        final ParameterViewMultiGraphPanel thisView = this;
        // sorting of components DOWN
        // TODO: is there an easier or faster way of achieving this?
        
     

        
        
        //TODO: adjust constant
        final double timeFrameStep = 0.1;
        final double timeFrameIterativeStep = 0.05;
        
        narrowerButton.addMouseListener(new MouseListener() 
        {
            Timer timer = new Timer();
			@Override
			public void mouseReleased(MouseEvent arg0) 
			{
				timer.cancel();
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) 
			{
				double timeFrame = linkedVisualization.getTimeFrame();
            	linkedVisualization.setTimeFrame(timeFrame + timeFrameStep * timeFrame);
				timer = new Timer();
				timer.schedule( new TimerTask() {

					@Override
					public void run() 
					{
						double timeFrame = linkedVisualization.getTimeFrame();
		            	linkedVisualization.setTimeFrame(timeFrame + timeFrameIterativeStep * timeFrame);
					}
		        	  
		          }, 700, 50);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
        
        broaderButton.addMouseListener(new MouseListener() 
        {
			@Override
			public void mouseReleased(MouseEvent arg0) 
			{
				timer.cancel();
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) 
			{
				double timeFrame = linkedVisualization.getTimeFrame();
            	linkedVisualization.setTimeFrame(timeFrame - timeFrameStep * timeFrame);
				timer = new Timer();
				timer.schedule( new TimerTask() {

					@Override
					public void run() 
					{
						double timeFrame = linkedVisualization.getTimeFrame();
		            	linkedVisualization.setTimeFrame(timeFrame - timeFrameIterativeStep * timeFrame);
					}
		        	  
		          }, 700, 50);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
        
       

	}	
	
//	private void update()
//	{
//	
//	}

	@Override
	public void resetPriority() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float updateAndGetPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public Dimension getGLPanelSize()
	{
		return glPanel.getSize();
	}
	

}
