package ranking;
public class Node {
	private String nodeid=null;
	private double edgeWeight=0.0;
	public Node(String id,double weight) {
		this.nodeid=id;
		this.edgeWeight=weight;
	}
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public double getEdgeWeight() {
		return edgeWeight;
	}

	public void setEdgeWeight(double edgeWeight) {
		this.edgeWeight = edgeWeight;
	}
}
