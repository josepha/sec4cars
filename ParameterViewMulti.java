// container class for the visualizations
// will contain UI components to control output behavior 
// TODO: extend

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


// TODO implement stuff belonging to the view
public class ParameterViewMulti extends AbstractView
{
	private static final long serialVersionUID = 1L;
	
	// visualization for this data block view
	private AbstractVisualization linkedVisualization = null;
	
    //*********************************************************************
	
	//private Timer timer;
	
	// set up GUI components
	
	// canvas for openGL rendering
	//private GLJPanel glPanel;
	// label for block name
	private JLabel nameLabel;
	// panel holding buttons below graph
	private JPanel buttonsPanel;
	private JPanel titlePanel;
	// button for closing panel
	//private JButton closeButton;
	// buttons for switching view order
	//private JButton switchUpButton;
	//private JButton switchDownButton;
	// buttons and label for setting priority
	private JButton priorityPlusButton;
	private JButton priorityMinusButton;
	private JTextField priorityField;
	
	private JCheckBox autoAdjustBox;
	
	//private JLayeredPane layeredPane;
	
	// value range labels
	private JTextField minVal;
	private JTextField maxVal;
	private JLabel minLabel;
	private JLabel maxLabel;
	
	// graph color label
	private JLabel colorLabel;


	
	private int priority;			//aktuelle priorität
	private int priorityCount;		//prioritätscounter
	private int priorityMax;		//priorität*activeViewCount
	private int activeViewCount;	//aktive graphPanel
	
	private String ID;
	private int position = 0;
	
	public void setVisualization(AbstractVisualization visualization)
	{		
		//glPanel.addGLEventListener(visualization);
		linkedVisualization = visualization;
		visualization.setContainingView(this);
		updateRangeFields();
		GraphColor col = linkedVisualization.getGraphColor(position);
		// could not be returned by the view
		if(col != null) colorLabel.setBackground(col.toJColor());
		// show that color of this visualisation ist not editable
		else colorLabel.setOpaque(false);
	}
	
	public AbstractVisualization getVisualisation()	{
		return linkedVisualization;
	}
	
	// helper for updating textfields holding color range
	public void updateRangeFields()
	{
		if(linkedVisualization == null) return;
		minVal.setText( String.format("%.3f", linkedVisualization.getMinValue(position)) );
		maxVal.setText( String.format("%.3f", linkedVisualization.getMaxValue(position)) );
	}

	public ParameterViewMulti(String name, int count, String panelID, final int position) 
	{
		// parameter view setup
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.position=position;
		
		priority = 1;
		this.activeViewCount=count;
		priorityCount=1;
		priorityMax=activeViewCount*priority;
		
		this.ID=panelID;
		
		// pass name to label
		// spaces are used to shift text slightly to the right
		nameLabel = new JLabel(name);
		Font font = new Font("Arial", Font.BOLD, 14);
		nameLabel.setFont(font);
		nameLabel.setHorizontalAlignment(SwingConstants.LEADING);
		int buttonSize = 28;
		// set up close button
//		ImageIcon close = new ImageIcon("close.png");
//		closeButton = new JButton(close);
//		closeButton.setMinimumSize(new Dimension(24,24));
//		closeButton.setPreferredSize(new Dimension(24, 24));
//		closeButton.setMaximumSize(new Dimension(24, 24));
		// set up switch buttons
//		ImageIcon switchUp = new ImageIcon("switchUp.png");
//		ImageIcon switchDown = new ImageIcon("switchDown.png");
//		switchUpButton = new JButton(switchUp);
//		switchUpButton.setMinimumSize(new Dimension(buttonSize,buttonSize));
//		switchUpButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
//		switchUpButton.setMaximumSize(new Dimension(buttonSize, buttonSize));
//		switchDownButton = new JButton(switchDown);
//		switchDownButton.setMinimumSize(new Dimension(buttonSize,buttonSize));
//		switchDownButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
//		switchDownButton.setMaximumSize(new Dimension(buttonSize, buttonSize));

		// setup autoAdjust gui
		autoAdjustBox = new JCheckBox("Auto-Skalierung");
		autoAdjustBox.setSelected(true);
		
		// set up priority buttons and label
		ImageIcon priorityPlus = new ImageIcon("plus.png");
		ImageIcon priorityMinus = new ImageIcon("minus.png");
		priorityPlusButton = new JButton(priorityPlus);
		priorityPlusButton.setMinimumSize(new Dimension(buttonSize,buttonSize));
		priorityPlusButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
		priorityPlusButton.setMaximumSize(new Dimension(buttonSize, buttonSize));
		priorityMinusButton = new JButton(priorityMinus);
		priorityMinusButton.setMinimumSize(new Dimension(buttonSize,buttonSize));
		priorityMinusButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
		priorityMinusButton.setMaximumSize(new Dimension(buttonSize, buttonSize));
		priorityField = new JTextField(priority+"");
		priorityField.setMinimumSize(new Dimension(20, 20));
		priorityField.setPreferredSize(new Dimension(20, 20));
		priorityField.setMaximumSize(new Dimension(20, 20));
		priorityField.setHorizontalAlignment(SwingConstants.CENTER);
		// value range labels and text fields
		minLabel = new JLabel("Min ");
		minLabel.setMinimumSize(new Dimension(30, 30));
		minLabel.setPreferredSize(new Dimension(30, 30));
		minLabel.setMaximumSize(new Dimension(30, 30));
		minLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		maxLabel = new JLabel("Max ");
		maxLabel.setMinimumSize(new Dimension(30, 30));
		maxLabel.setPreferredSize(new Dimension(30, 30));
		maxLabel.setMaximumSize(new Dimension(30, 30));
		maxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		minVal = new JTextField();
		minVal.setMinimumSize(new Dimension(60, 20));
		minVal.setPreferredSize(new Dimension(60, 20));
		minVal.setMaximumSize(new Dimension(60, 20));
		minVal.setHorizontalAlignment(JTextField.RIGHT);
		maxVal = new JTextField();
		maxVal.setMinimumSize(new Dimension(60, 20));
		maxVal.setPreferredSize(new Dimension(60, 20));
		maxVal.setMaximumSize(new Dimension(60, 20));
		maxVal.setHorizontalAlignment(JTextField.RIGHT);
		// set up openGL graph panel
//		GLProfile glprofile = GLProfile.getDefault();
//        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
//        glPanel = new GLJPanel( glcapabilities );
//        glPanel.setMinimumSize(new Dimension(50,300));
//        glPanel.setPreferredSize(new Dimension(400,300));
//        glPanel.setMaximumSize(new Dimension(1500,300));
        // set up panel holding buttons
		buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.setMinimumSize(new Dimension(50,35));
        buttonsPanel.setPreferredSize(new Dimension(400, 35));
        buttonsPanel.setMaximumSize(new Dimension(1500, 35));
        // set up panel holding title and close button
// 		titlePanel = new JPanel();
// 		titlePanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
// 		titlePanel.setMinimumSize(new Dimension(50,35));
// 		titlePanel.setPreferredSize(new Dimension(400, 35));
// 		titlePanel.setMaximumSize(new Dimension(1500, 35));
        // graph color label
        colorLabel = new JLabel();
        colorLabel.setOpaque(true);
        colorLabel.setBackground(Color.RED);
        colorLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        colorLabel.setMinimumSize(new Dimension(60, 20));
        colorLabel.setPreferredSize(new Dimension(60, 20));
        colorLabel.setMaximumSize(new Dimension(60, 20));
        
//        // TODO: remove later on
//        FPSAnimator animator = new FPSAnimator(glPanel, 60);
//        animator.add(glPanel); //???
//        animator.start();
//        add(Box.createHorizontalGlue());
           
        add(new JSeparator());
        int minDist = 7;
        // set alignments and add components
//        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
//        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
//        titlePanel.setMinimumSize(new Dimension(50, 30));
//        titlePanel.setPreferredSize(new Dimension(400, 30));
//        titlePanel.setMaximumSize(new Dimension(1500, 30));
//        titlePanel.add(Box.createHorizontalStrut(minDist));
        buttonsPanel.add(nameLabel);
//        titlePanel.add(Box.createHorizontalGlue());
//        titlePanel.add(Box.createHorizontalStrut(minDist));
//        //titlePanel.add(closeButton);
//        titlePanel.setAlignmentX(LEFT_ALIGNMENT);
        
        
//       layeredPane = new JLayeredPane();
//        layeredPane.setLayout(new BoxLayout(layeredPane, BoxLayout.LINE_AXIS));
//       //com.sun.lwuit.layouts.Layout
//        layeredPane.setAlignmentX(LEFT_ALIGNMENT);
//        layeredPane.add(glPanel);
//        glPanel.setAlignmentX(Box.LEFT_ALIGNMENT);
//        layeredPane.setMinimumSize(new Dimension(50,50));
//        layeredPane.setPreferredSize(new Dimension(400,50));
//        layeredPane.setMaximumSize(new Dimension(1500,50));
//        //TODO: find out how to render label on top of view
////        JLabel testLabel = new JLabel("TEST");
////        layeredPane.add(testLabel);
//        //testLabel.setAlignmentX(Box.CENTER_ALIGNMENT);
//        //TODO: add layered labels of min, max and units
//        add(layeredPane);
        //buttonsPanel.setAlignmentX(RIGHT_ALIGNMENT);
        add(Box.createHorizontalGlue());
        buttonsPanel.add(Box.createHorizontalStrut(minDist));
        buttonsPanel.add(colorLabel);
        
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(Box.createHorizontalStrut(minDist));
        buttonsPanel.add(priorityPlusButton);
        buttonsPanel.add(Box.createHorizontalStrut(minDist));
        buttonsPanel.add(priorityField);
        buttonsPanel.add(Box.createHorizontalStrut(minDist));
        buttonsPanel.add(priorityMinusButton);
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(Box.createHorizontalStrut(minDist));
        buttonsPanel.add(minLabel);
        buttonsPanel.add(minVal);
        buttonsPanel.add(maxLabel);
        buttonsPanel.add(maxVal);
        buttonsPanel.add(Box.createHorizontalStrut(minDist));
        buttonsPanel.add(autoAdjustBox);
        buttonsPanel.add(Box.createHorizontalGlue());
//        buttonsPanel.add(Box.createHorizontalStrut(minDist));
//        buttonsPanel.add(switchUpButton);
//        buttonsPanel.add(switchDownButton);
        buttonsPanel.add(Box.createHorizontalStrut(minDist));
        add(buttonsPanel);
        add(Box.createHorizontalGlue());
        add(Box.createVerticalStrut(minDist));
        setAlignmentX(LEFT_ALIGNMENT);
        
        
        //*********************************************************************
        
        // needs to be passed to buttonListeners
        final ParameterViewMulti thisView = this;
        // sorting of components DOWN
        // TODO: is there an easier or faster way of achieving this?
//        switchDownButton.addActionListener(new ActionListener() 
//        {
//            public void actionPerformed(ActionEvent e) 
//            {
//            	// get components of parent view (the JScrollPane of MainView)
//            	Container parent = thisView.getParent();
//            	// save components
//            	Component[] components = parent.getComponents();
//            	int len = components.length;
//            	// out of bounds
//            	if(components[len-1] == thisView) return;
//            	// clear all components
//            	thisView.getParent().removeAll();
//            	// search for this component in list
//            	// if found -> swap with successor
//            	for(int i=0; i<len; ++i)
//            	{
//            		Component temp = components[i];
//            		if(temp == thisView)
//            		{
//            			 components[i] = components[i+1];
//                         components[i+1] = temp;
//                         //System.out.format("Swapped items &d and %d\n", i, i+1);
//                         break;  
//            		}
//            	}
//            	// add components again
//            	for (Component comp : components)
//                {
//                    parent.add(comp);
//                }
//            	// lay out components again
//            	parent.revalidate();
//            } 
//        });
//        
//        // sorting of components UP
//        switchUpButton.addActionListener(new ActionListener() 
//        {
//            public void actionPerformed(ActionEvent e) 
//            {
//            	Container parent = thisView.getParent();
//            	Component[] components = parent.getComponents();
//            	int len = components.length;
//            	// out of bounds
//            	if(components[1] == thisView) return;
//            	thisView.getParent().removeAll();
//            	for(int i=0; i<len; ++i)
//            	{
//            		Component temp = components[i];
//            		if(temp == thisView)
//            		{
//            			 components[i] = components[i-1];
//                         components[i-1] = temp;
//                         //System.out.format("Swapped items &d and %d\n", i, i+1);
//                         break;  
//            		}
//            	}
//            	for (Component comp : components)
//                {
//                    parent.add(comp);
//                }       
//            	thisView.getParent().validate(); 
//            }   
//        });
        
        priorityPlusButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	// increment label integer by one
            	priority =  Math.min(priority+1,10);
            	priorityField.setText(priority+"");
            	priorityMax=activeViewCount*priority;
            }
        });
        
        priorityMinusButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	// decrement label integer by one
            	priority =  Math.max(priority-1,1);
            	priorityField.setText(priority+"");
            	priorityMax=activeViewCount*priority;
            }  
        });
        
//        priorityPlusButton.addActionListener(new ActionListener()  //???
//        {
//            public void actionPerformed(ActionEvent e) 
//            {
//            	// increment label integer by one
//            	int newVal = Math.min(Integer.parseInt(priorityField.getText())+1,10);
//            	priorityField.setText(String.valueOf(newVal));
//            }  
//        });
        
        minVal.addKeyListener(new KeyListener() 
        {
			@Override
			public void keyPressed(KeyEvent arg0) 
			{
			}

			@Override
			public void keyReleased(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					linkedVisualization.setMinValue(Double.parseDouble(minVal.getText().replace(',', '.')),position);
					// disable auto adjust because value was probably changed on purpose
					autoAdjustBox.setSelected(false);
					linkedVisualization.setAutoAdjust(false);
					updateRangeFields();
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) 
			{
			}  
        });

        maxVal.addKeyListener(new KeyListener() 
        {
			@Override
			public void keyPressed(KeyEvent arg0) 
			{
			}

			@Override
			public void keyReleased(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{					
					linkedVisualization.setMaxValue(Double.parseDouble(maxVal.getText().replace(',', '.')),position);
					// disable auto adjust because value was probably changed on purpose
					autoAdjustBox.setSelected(false);
					linkedVisualization.setAutoAdjust(false);
					updateRangeFields();
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) 
			{
			}  
        });
      
        final int bla = position;
        
        colorLabel.addMouseListener(new MouseListener() 
        {
			@Override
			public void mouseReleased(MouseEvent arg0) 
			{
				
				Color newColor = JColorChooser.showDialog(thisView, "Pick Graph Color", linkedVisualization.getGraphColor(bla).toJColor());
				if(newColor != null)
				{
					linkedVisualization.setGraphColor(new GraphColor(newColor),bla);
					colorLabel.setBackground(newColor);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
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
        
        //TODO: adjust constant
        //final double timeFrameStep = 0.1;
        //final double timeFrameIterativeStep = 0.05;
        
        
        
        
        
//        closeButton.addActionListener(new ActionListener() 
//        {
//            public void actionPerformed(ActionEvent e) 
//            {
//            	JPanel parent = (JPanel)thisView.getParent();
//            	// remove view
//            	parent.remove(thisView);
//            	//parent.revalidate();
//            	parent.updateUI();
//            }  
//        });
        
        autoAdjustBox.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	linkedVisualization.setAutoAdjust(autoAdjustBox.isSelected());
            }  
        });
	}	
	
//	private void update()
//	{
//		
//	}
	
	public float updateAndGetPriority()
	{
		priorityCount++;
//		System.out.println(nameLabel.getText()+" "+(float)priorityCount/(float)priorityMax);
		return (float)priorityCount/(float)priorityMax;
	}
	
	public void resetPriority()
	{
		priorityCount=0;
	}
	
	public String getID()
	{
		return ID;
	}
}
