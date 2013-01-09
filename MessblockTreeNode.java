import javax.swing.tree.DefaultMutableTreeNode;

public class MessblockTreeNode extends DefaultMutableTreeNode{

	int nodeID;

	public MessblockTreeNode (String name, int nodeID)
	{
		super(name);
		this.nodeID=nodeID;
	}
	
	public int getNodeID()
	{
		return nodeID;
	}
}
