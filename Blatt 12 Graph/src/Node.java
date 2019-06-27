import java.util.ArrayList;

public class Node {

	private ArrayList<Edge> adzent;
	private int id;
	
	public Node (int pid) {
		id = pid;
		adzent = new ArrayList<Edge>();
	}

	public  ArrayList<Edge> getAdzent() {
		return adzent;
	}

	public int getId() {
		return id;
	}
	
	public void addEdge(Node dst) {
		adzent.add(new Edge(this, dst));
	}
	
	public boolean equals(Node other) {
		if (this.id == other.getId()) return true;
		else return false;
	}
	
	public String toString() {
		String build = "";
		for (Edge edge : adzent) {
			build = build + ", " + edge.getDst().getId();
		}
		return build;
	}
	
}
