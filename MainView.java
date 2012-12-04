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

import com.jogamp.opengl.util.FPSAnimator;

// Main view with layout
// contends so far: the frame holds a screen filling horizJSplitPane, which has a JTree as its left,
// and a JScrollPane with a JLabel holding multiple GLJPanels as its right component
// TODO: why is JScrollPane not scrolling?
public class MainView extends JFrame
{
	private static final long serialVersionUID = 1L;

	
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
		DefaultMutableTreeNode j623_m001_01 = new DefaultMutableTreeNode("001/1 Engine Speed (RPM)");
		DefaultMutableTreeNode j623_m002_04 = new DefaultMutableTreeNode("002/4 Mass Airflow (g/s)");
		DefaultMutableTreeNode j623_m003_03 = new DefaultMutableTreeNode("003/3 Throttle valve angle (%)");
		DefaultMutableTreeNode j623_m010_04 = new DefaultMutableTreeNode("010/4 Ignition angle (°BTDC °KW ???)");
		DefaultMutableTreeNode j623_m004_02 = new DefaultMutableTreeNode("004/2 Battery voltage (V)");
		DefaultMutableTreeNode j623_m006_04 = new DefaultMutableTreeNode("006/4 Altitude correction (%)");
		
		//TODO weitere steuergeräte: messwerte eintragen
		DefaultMutableTreeNode j624_default = new DefaultMutableTreeNode("empty");
		DefaultMutableTreeNode j104_default = new DefaultMutableTreeNode("empty");
		DefaultMutableTreeNode j234_default = new DefaultMutableTreeNode("empty");
		DefaultMutableTreeNode j217_default = new DefaultMutableTreeNode("empty");
		DefaultMutableTreeNode j431_default = new DefaultMutableTreeNode("empty");
		DefaultMutableTreeNode j197_default = new DefaultMutableTreeNode("empty");
		DefaultMutableTreeNode j502_default = new DefaultMutableTreeNode("empty");
		DefaultMutableTreeNode j518_default = new DefaultMutableTreeNode("empty");

		//j623 motorsteuergerät
		j623.add(j623_m001_01);
		j623.add(j623_m002_04);
		j623.add(j623_m003_03);
		j623.add(j623_m010_04);
		j623.add(j623_m004_02);
		j623.add(j623_m006_04);
		
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
	
	public MainView(String title)
	{
		super(title);
		setSize(1024, 768);
		Container contentPane = this.getContentPane();
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		// quit program when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        JPanel graphPanel = new JPanel();
		graphPanel.setLayout( new BoxLayout(graphPanel, BoxLayout.Y_AXIS) );
		// scrollpane holding the visualizations
		JScrollPane graphListPane = new JScrollPane(graphPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		for(int i = 0; i < 10; ++i)
	    {
			// TODO: replace GLJPanel with ParameterView once implemented
			ParameterView panel = new ParameterView( "name " + i );
	        if(i % 2 == 0) panel.setVisualization(new ParameterGraph(2.5, 0, 45));
	        else panel.setVisualization(new ParameterQuad(-1.25, 1.25, 0.0));
	        panel.setMinimumSize(new Dimension(500, 300));
	        graphPanel.add(panel);
	        graphPanel.setMinimumSize(new Dimension(500, i * 400));
	    }
		// fill tree
		DefaultMutableTreeNode dataRoot = new DefaultMutableTreeNode("Steuergeräte");
		JTree dataBlocksTree = new JTree(dataRoot);
		initilaizeTree(dataRoot);
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
