import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.*;
//import javax.swing.tree.DefaultMutableTreeNode;

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
	
	public MainView(String title)
	{
		super(title);
		setSize(1024, 768);
		Container contentPane = this.getContentPane();
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		// quit program when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		graphPanel = new JPanel();
		graphPanel.setLayout( new BoxLayout(graphPanel, BoxLayout.Y_AXIS) );
		// scrollpane holding the visualizations
		JScrollPane graphListPane = new JScrollPane(graphPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		for(int i = 0; i < 10; ++i)
	    {
			// TODO: replace GLJPanel with ParameterView once implemented
			ParameterView panel = new ParameterView( "name " + i );
	        panel.setVisualization(new GraphVisualization(2.5, 0, 45));
	        panel.setMinimumSize(new Dimension(500, 300));
	        graphPanel.add(panel);
	        graphPanel.setMinimumSize(new Dimension(500, i * 400));
	    }
		// fill tree
		DefaultMutableTreeNode dataRoot = new DefaultMutableTreeNode("Steuergeräte");
		dataBlocksTree = new JTree(dataRoot);
		DefaultMutableTreeNode j623_01 = new DefaultMutableTreeNode("J623 Motorsteuergerät");
		DefaultMutableTreeNode j624_02 = new DefaultMutableTreeNode("J624 Motorsteuergerät 2");
		DefaultMutableTreeNode j104_03 = new DefaultMutableTreeNode("J104 ABS");
		DefaultMutableTreeNode j234_04 = new DefaultMutableTreeNode("J234 Airbag");
		DefaultMutableTreeNode j217_05 = new DefaultMutableTreeNode("J217 automatisches Getriebe");
		DefaultMutableTreeNode j431_06 = new DefaultMutableTreeNode("J431 Leuchtweitenregelung");
		DefaultMutableTreeNode j197_07 = new DefaultMutableTreeNode("J197 Niveauregelung");
		DefaultMutableTreeNode j502_08 = new DefaultMutableTreeNode("J502 Reifendruckkontrolle");
		DefaultMutableTreeNode j518_09 = new DefaultMutableTreeNode("J518 Zugang und Startberechtigung");
		
		DefaultMutableTreeNode j01_m00x_0x = new DefaultMutableTreeNode("00x/x Engine Speed Sensor");
		DefaultMutableTreeNode j01_m00y_0x = new DefaultMutableTreeNode("00x/x Mass Airflow Sensor");
//		DefaultMutableTreeNode j623_m001 = new DefaultMutableTreeNode("Intake manifold pressure");
//		DefaultMutableTreeNode j623_m001 = new DefaultMutableTreeNode("Messblock 001");
//		DefaultMutableTreeNode j623_m001 = new DefaultMutableTreeNode("Messblock 001");
//		DefaultMutableTreeNode j623_m001 = new DefaultMutableTreeNode("Messblock 001");
//		general.add(new DefaultMutableTreeNode("stuff"));
//		DefaultMutableTreeNode engine = new DefaultMutableTreeNode("Engine");
//		engine.add(new DefaultMutableTreeNode("stuff"));
//		DefaultMutableTreeNode tires = new DefaultMutableTreeNode("Tires");
//		tires.add(new DefaultMutableTreeNode("stuff"));
//		DefaultMutableTreeNode environment = new DefaultMutableTreeNode("Environment");
//		environment.add(new DefaultMutableTreeNode("stuff"));
//		dataRoot.add(general);
//		dataRoot.add(engine);
//		dataRoot.add(tires);
//		dataRoot.add(environment);
		j623_01.add(j01_m00x_0x);
		j623_01.add(j01_m00y_0x);
		dataRoot.add(j623_01);
		dataRoot.add(j624_02);
		dataRoot.add(j104_03);
		dataRoot.add(j234_04);
		dataRoot.add(j217_05);
		dataRoot.add(j431_06);
		dataRoot.add(j197_07);
		dataRoot.add(j502_08);
		dataRoot.add(j518_09);
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
		
		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BoxLayout(treePanel, BoxLayout.PAGE_AXIS));		
		//treePanel.setAlignmentX(LEFT_ALIGNMENT);
		treePanel.add(dataBlocksTree);
		treePanel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel leftContainer = new JPanel();
		leftContainer.setLayout(new BoxLayout(leftContainer, BoxLayout.PAGE_AXIS));		
		//leftContainer.setAlignmentX(RIGHT_ALIGNMENT);
		//JPanel rightContainer = new JPanel();
		
		leftContainer.add(treePanel);
		leftContainer.add(buttonsPanel);
		
	    //rightContainer.add(graphListPane);

		// setup split pane
        //mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dataBlocksTree, graphListPane);
		JSplitPane  mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftContainer, graphListPane);//rightContainer);
        mainSplitPane.setDividerLocation(200);
        mainSplitPane.setContinuousLayout(true);

        contentPane.add(mainSplitPane, BorderLayout.CENTER);
	}

}
