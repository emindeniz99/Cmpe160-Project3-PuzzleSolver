import java.util.ArrayList;

public class Node {
	int[] data;
	Node parent;
	ArrayList<Node> childs;
	int zeroLoc;
	char lastMove;

	// root
	public Node(int[] data, char newCommand, int zeroLoc) {
		this.data = data.clone();
		childs = new ArrayList<Node>();

		this.zeroLoc = zeroLoc;
	}

	// childs
	public Node(Node parent, int[] data, char newCommand, int zeroLoc) {
		this.data = data.clone();
		childs = new ArrayList<Node>();
		this.parent = parent;
		this.zeroLoc = zeroLoc;
		this.lastMove = newCommand;
	}

	public void addChild(Node childNode) {
		childs.add(childNode);

	}

}
