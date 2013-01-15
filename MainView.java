import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
//import javax.swing.*;
//import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

// Main view with layout
// contends so far: the frame holds a screen filling horizJSplitPane, which has a JTree as its left,
// and a JScrollPane with a JLabel holding multiple GLJPanels as its right component
// TODO: why is JScrollPane not scrolling?
public class MainView extends JFrame
{
	private static final long serialVersionUID = 1L;

	// graph panel
	private JPanel graphPanel;
	
	// tree containing car data types
	private JTree dataBlocksTree;
	
	private JFileChooser fileChooser;
	boolean aufnahmeAktiv;
	File aufnahmeFile;
	
	// getter für graphPanel, den Container, der die Graphansichten enthält
	// @josepha: hier über .add die Visualisierungen der Motorblöcke hinzufügen
	public JPanel getGraphPanel()
	{
		return graphPanel;
	}
	
	private void initilaizeTree(DefaultMutableTreeNode dataRoot)	{
		//TODO aus datei laden?
		
		//alle steuergeräte
		DefaultMutableTreeNode j623 = new DefaultMutableTreeNode("J623 Motorsteuergerät");
		DefaultMutableTreeNode j624 = new DefaultMutableTreeNode("J624 Motorsteuergerät 2");
		DefaultMutableTreeNode j104 = new DefaultMutableTreeNode("J104 ABS");
		DefaultMutableTreeNode j234 = new DefaultMutableTreeNode("J234 Airbag");
		DefaultMutableTreeNode j217 = new DefaultMutableTreeNode("J217 automatisches Getriebe");
		DefaultMutableTreeNode j431 = new DefaultMutableTreeNode("J431 Leuchtweitenregelung");
		DefaultMutableTreeNode j197 = new DefaultMutableTreeNode("J197 Niveauregelung");
		DefaultMutableTreeNode j502 = new DefaultMutableTreeNode("J502 Reifendruckkontrolle");
		DefaultMutableTreeNode j518 = new DefaultMutableTreeNode("J518 Zugang und Startberechtigung");
		
		//j623 motorsteuergerät messwerte
		MessblockTreeNode j623_m001_01 = new MessblockTreeNode("001/1",6230011);
		MessblockTreeNode j623_m002_04 = new MessblockTreeNode("002/4",6230024);
		MessblockTreeNode j623_m003_03 = new MessblockTreeNode("003/3",6230033);
		MessblockTreeNode j623_m010_04 = new MessblockTreeNode("010/4",6230104);
		MessblockTreeNode j623_m004_02 = new MessblockTreeNode("004/2",6230042);
		MessblockTreeNode j623_m006_04 = new MessblockTreeNode("006/4",6230064);
		MessblockTreeNode j623_m007_01 = new MessblockTreeNode("007/1",6230071);
		MessblockTreeNode j623_m007_02 = new MessblockTreeNode("007/2",6230072);
		MessblockTreeNode j623_m007_03 = new MessblockTreeNode("007/3",6230073);
		MessblockTreeNode j623_m007_04 = new MessblockTreeNode("007/4",6230074);
		MessblockTreeNode j623_m007_05 = new MessblockTreeNode("007/5",6230075);
		
		//TODO weitere steuergeräte: messwerte eintragen
		MessblockTreeNode j624_default = new MessblockTreeNode("empty",0);
		MessblockTreeNode j104_default = new MessblockTreeNode("empty",0);
		MessblockTreeNode j234_default = new MessblockTreeNode("empty",0);
		MessblockTreeNode j217_default = new MessblockTreeNode("empty",0);
		MessblockTreeNode j431_default = new MessblockTreeNode("empty",0);
		MessblockTreeNode j197_default = new MessblockTreeNode("empty",0);
		MessblockTreeNode j502_default = new MessblockTreeNode("empty",0);
		MessblockTreeNode j518_default = new MessblockTreeNode("empty",0);

		//j623 motorsteuergerät
		j623.add(j623_m001_01);
		j623.add(j623_m002_04);
		j623.add(j623_m003_03);
		j623.add(j623_m010_04);
		j623.add(j623_m004_02);
		j623.add(j623_m006_04);
		j623.add(j623_m007_01);
		j623.add(j623_m007_02);
		j623.add(j623_m007_03);
		j623.add(j623_m007_04);
		j623.add(j623_m007_05);
		
		//TODO weitere steuergeräte: messwerte einfügen
		j624.add(j624_default);
		j104.add(j104_default);
		j234.add(j234_default);
		j217.add(j217_default);
		j431.add(j431_default);
		j197.add(j197_default);
		j502.add(j502_default);
		j518.add(j518_default);
		
		dataRoot.add(j623);
		dataRoot.add(j624);
		dataRoot.add(j104);
		dataRoot.add(j234);
		dataRoot.add(j217);
		dataRoot.add(j431);
		dataRoot.add(j197);
		dataRoot.add(j502);
		dataRoot.add(j518);	
		
	}
	
	private void changeTitle(String newTitle)
	{
	    this.setTitle(newTitle);
	}
	
	public MainView(String title)
	{
		super(title);
		setSize(1024, 768);
		Container contentPane = this.getContentPane();
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		// quit program when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("log Datei","log"));
        
        aufnahmeFile=null;
        aufnahmeAktiv= false;
		
		graphPanel = new JPanel();
		graphPanel.setLayout( new BoxLayout(graphPanel, BoxLayout.Y_AXIS) );
		// scrollpane holding the visualizations
		JScrollPane graphListPane = new JScrollPane(graphPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		//@josepha
//		for(int i = 0; i < 10; ++i)
//	    {
//			// TODO: replace GLJPanel with ParameterView once implemented
//			ParameterView panel = new ParameterView( "name " + i );
//	        panel.setVisualization(new GraphVisualization(2.5, 0, 45));
//	        panel.setMinimumSize(new Dimension(500, 300));
//	        graphPanel.add(panel);
//	        graphPanel.setMinimumSize(new Dimension(500, i * 400));
//	    }
		
		// fill tree
		DefaultMutableTreeNode dataRoot = new DefaultMutableTreeNode("Steuergeräte");
		dataBlocksTree = new JTree(dataRoot);		
		dataBlocksTree.setSelectionModel(new DefaultTreeSelectionModel(){	
			private static final long serialVersionUID = 1L;				
			 
			@Override
			public void addSelectionPath(TreePath treePath) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)treePath.getLastPathComponent();
    			if(node.isLeaf()){
    				super.addSelectionPath(treePath);
    			}				
			}	
			
			@Override
			public void setSelectionPath(TreePath treePath) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)treePath.getLastPathComponent();
    			if(node.isLeaf()){
    				super.setSelectionPath(treePath);
    			}				
			}	

			
		});
		
		initilaizeTree(dataRoot);		
		
		dataBlocksTree.expandRow(0);
		
		dataBlocksTree.setMinimumSize(new Dimension(300,300));
		dataBlocksTree.setPreferredSize(new Dimension(300,300));
		dataBlocksTree.setMaximumSize(new Dimension(300,300));
		dataBlocksTree.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel auswahlLabel = new JLabel(" Auswahl");
		JButton auswahlanzeigenButton = new JButton("anzeigen");
		//auswahlanzeigenButton.setPreferredSize(new Dimension(160, 160));
		auswahlanzeigenButton.setMinimumSize(new Dimension(80,25));
		auswahlanzeigenButton.setPreferredSize(new Dimension(80,25));
		auswahlanzeigenButton.setMaximumSize(new Dimension(80,25));
		JButton auswahlloeschenButton = new JButton("löschen");
		auswahlloeschenButton.setMinimumSize(new Dimension(80,25));
		auswahlloeschenButton.setPreferredSize(new Dimension(80,25));
		auswahlloeschenButton.setMaximumSize(new Dimension(80,25));
		JLabel aufnahmeLabel = new JLabel(" Aufnehmen");
		JButton aufnahmeButton = new JButton("starten");
		aufnahmeButton.setMinimumSize(new Dimension(80,25));
		aufnahmeButton.setPreferredSize(new Dimension(80,25));
		aufnahmeButton.setMaximumSize(new Dimension(80,25));
		JLabel anzeigeLabel = new JLabel(" Anzeige");
		JButton anzeigeButton = new JButton("single");
		anzeigeButton.setMinimumSize(new Dimension(80,25));
		anzeigeButton.setPreferredSize(new Dimension(80,25));
		anzeigeButton.setMaximumSize(new Dimension(80,25));
		//anzeigeButton.setAlignmentX(LEFT_ALIGNMENT);
		JLabel einstellungenLabel = new JLabel(" Einstellungen");
		JButton einstellungenladenButton = new JButton("laden");
		einstellungenladenButton.setMinimumSize(new Dimension(80,25));
		einstellungenladenButton.setPreferredSize(new Dimension(80,25));
		einstellungenladenButton.setMaximumSize(new Dimension(80,25));
		JButton einstellungenspeichernButton = new JButton("speichern");
		einstellungenspeichernButton.setMinimumSize(new Dimension(80,25));
		einstellungenspeichernButton.setPreferredSize(new Dimension(80,25));
		einstellungenspeichernButton.setMaximumSize(new Dimension(80,25));
		
		JLabel empty1= new JLabel("   ");
		JLabel empty2 = new JLabel("   ");		
		
		JPanel buttonsRightPanel = new JPanel();
		buttonsRightPanel.setLayout(new BoxLayout(buttonsRightPanel, BoxLayout.PAGE_AXIS));
		
		JPanel buttonsLeftPanel = new JPanel();
		buttonsLeftPanel.setLayout(new BoxLayout(buttonsLeftPanel, BoxLayout.PAGE_AXIS));
		buttonsLeftPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		
		buttonsLeftPanel.add(auswahlLabel);
		buttonsLeftPanel.add(Box.createRigidArea(new Dimension(0,3)));
		buttonsLeftPanel.add(auswahlanzeigenButton);
		buttonsLeftPanel.add(Box.createRigidArea(new Dimension(0,8)));
		buttonsLeftPanel.add(aufnahmeLabel);
		buttonsLeftPanel.add(Box.createRigidArea(new Dimension(0,3)));
		buttonsLeftPanel.add(aufnahmeButton);
		buttonsLeftPanel.add(Box.createRigidArea(new Dimension(0,8)));
		buttonsLeftPanel.add(einstellungenLabel);
		buttonsLeftPanel.add(Box.createRigidArea(new Dimension(0,3)));
		buttonsLeftPanel.add(einstellungenladenButton);
		buttonsLeftPanel.add(Box.createRigidArea(new Dimension(0,20)));
		
		buttonsRightPanel.add(empty1);
		buttonsRightPanel.add(Box.createRigidArea(new Dimension(0,3)));
		buttonsRightPanel.add(auswahlloeschenButton);
		buttonsRightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		buttonsRightPanel.add(anzeigeLabel);
		buttonsRightPanel.add(Box.createRigidArea(new Dimension(0,3)));
		buttonsRightPanel.add(anzeigeButton);
		buttonsRightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		buttonsRightPanel.add(empty2);
		buttonsRightPanel.add(Box.createRigidArea(new Dimension(0,3)));
		buttonsRightPanel.add(einstellungenspeichernButton);
		buttonsRightPanel.add(Box.createRigidArea(new Dimension(0,20)));

		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.add(buttonsLeftPanel);
		buttonsPanel.add(buttonsRightPanel);
		
//		JPanel treePanel = new JPanel();
//		treePanel.setLayout(new BoxLayout(treePanel, BoxLayout.PAGE_AXIS));		
//		//treePanel.setAlignmentX(LEFT_ALIGNMENT);
//		treePanel.add(dataBlocksTree);
//		treePanel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel leftContainer = new JPanel();
		leftContainer.setLayout(new BoxLayout(leftContainer, BoxLayout.PAGE_AXIS));		
		//leftContainer.setAlignmentX(RIGHT_ALIGNMENT);
		//JPanel rightContainer = new JPanel();
		
		JScrollPane treeScrollPane = new JScrollPane(dataBlocksTree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		leftContainer.add(treeScrollPane);
		leftContainer.add(buttonsPanel);
		
	    //rightContainer.add(graphListPane);

		// setup split pane
        //mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dataBlocksTree, graphListPane);
		JSplitPane  mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftContainer, graphListPane);//rightContainer);
        mainSplitPane.setDividerLocation(200);
        mainSplitPane.setContinuousLayout(true);

        contentPane.add(mainSplitPane, BorderLayout.CENTER);
        
        //baumauswahl => graphpanel anzeigen
        auswahlanzeigenButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
            {
        		//aktive graphpanel löschen
        		int activePanelsCount = graphPanel.getComponentCount();            	
            	for(int i =0;i<activePanelsCount;i++)
            	{
            		graphPanel.remove(0);
            	}
        		
            	//markierte knoten auslesen und graphpanel für jedes blatt starten
        		TreePath[] treePaths = dataBlocksTree.getSelectionPaths();
        		
        		for(int i=0;i<treePaths.length;i++)
        		{
        			MessblockTreeNode node = (MessblockTreeNode)treePaths[i].getLastPathComponent();        			
        			ParameterView panel = new ParameterView(node.toString(),treePaths.length, node.getNodeID());
        		    panel.setVisualization(new GraphVisualization(2.5, 0, 45));
        		    panel.setMinimumSize(new Dimension(500, 300));
        		    graphPanel.add(panel);
        		    graphPanel.setMinimumSize(new Dimension(500, i * 400));      	
        		}      
        		graphPanel.updateUI();
            }   
        });
        
        //aktive Graphpanel und Baumauswahl löschen        
        auswahlloeschenButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
            {
            	dataBlocksTree.clearSelection();
            	int activePanelsCount = graphPanel.getComponentCount();
            	
            	for(int i =0;i<activePanelsCount;i++)
            	{
            		graphPanel.remove(0);
            	}
            	graphPanel.updateUI();
            } 
        });
        
        
        aufnahmeButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
            {
        		if(aufnahmeAktiv==false)   //logging starten   
        		{
        			//ExampleFileFilter filter = new ExampleFileFilter();
        			//filter.addExtension("jpg");
        			int returnVal = fileChooser.showOpenDialog(null);
        			if (returnVal == JFileChooser.APPROVE_OPTION) {
        				aufnahmeFile = fileChooser.getSelectedFile();
        				//This is where a real application would open the file.
        				//log.append("Opening: " + file.getName() + "." + newline);
        				if(aufnahmeFile.exists())
        				{
        					System.out.println("File: "+aufnahmeFile.toString());
        				}
        				else //log-datei anlegen
        				{                    	
        					if(!aufnahmeFile.getPath().toLowerCase().endsWith(".log"))
        					{
        						aufnahmeFile = new File(aufnahmeFile.getPath() + ".log");
        					}
        					try {
        						aufnahmeFile.createNewFile();
        					} catch (IOException e1) {
        						// TODO Auto-generated catch block
        						e1.printStackTrace();
        					}
        					System.out.println("new File: "+aufnahmeFile.toString());
        				}
        				aufnahmeAktiv=true;
        			    ((JButton) e.getSource()).setText("stoppen");  //schön is anders
        			    changeTitle("CarInspector "+aufnahmeFile.toString());
        			    
        			}
                }
        		else //logging stoppen
        		{
        			aufnahmeAktiv=false;
        			aufnahmeFile=null;
        			((JButton) e.getSource()).setText("starten");  
        			changeTitle("CarInspector");
        		}
            } 
        });
	}

}
