import javax.swing.tree.DefaultMutableTreeNode;

public class MessblockTreeNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = 1L;
	String nodeID;

	public MessblockTreeNode (String name, String nodeID)
	{
		super(name);
		this.nodeID=nodeID;
	}
	
	public String getNodeID()
	{
		return nodeID;
	}
}
