import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("bitte geben sie nur 2 Parameter ein!");
			return;
		}
		int stardID;
		try {
			stardID = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e) {
			System.out.println("2.Parameter = positiver int!");
			return;
		}
		if (stardID < 0) {
			System.out.println("2.Parameter = positiver int!");
			return;
		}
		
		Graph test; 
		
		String pfad = args[0];
		test = Graph.fromFile(pfad);
		test.ausgeben();
		test.breitenSuche(stardID);
	}

	public ArrayList<Node> nodes;
	
	public Graph() {
		nodes = new ArrayList<Node>();
	}
	
	public boolean contains( int pid){
		for (Node n : nodes) {
			if (n.getId() == pid) return true;
		}
		return false;
	}
	
	public boolean addNode( int pid) {
		if (contains(pid)) {
			return false;
		}
		else {
			nodes.add(new Node(pid));
			return true;
		}
	}
	
	public Node getNode(int pid) {
		for (Node n : nodes) {
			if (n.getId() == pid) return n;
		}
		return null;
	}
	
	public void addEdge( int src, int dst) {
		if (!contains(src)) addNode(src);
		if (!contains(dst)) addNode(dst);
		
		Node a = getNode(src);
		Node b = getNode(dst);
		
		a.addEdge(b);
		b.addEdge(a);
	}

	public static Graph fromFile(String filepath) {
		BufferedReader br = null;
		
		try{
			br = new BufferedReader( new FileReader(filepath) );
		} 
		catch (FileNotFoundException e){
			System.out.println("File not found");
			System.exit(1);
		}
		
		String zeile;
		Graph fileGraph = new Graph();
		
		try{
			while ((zeile = br.readLine()) != null){
				System.out.println(zeile);
				int a = Integer.parseInt(zeile.substring(0, zeile.indexOf(",")));
				int b = Integer.parseInt(zeile.substring(zeile.indexOf(",") + 1, zeile.length()));
				fileGraph.addEdge(a, b);
			}
		}
		catch(IOException e){
			System.out.println("Fehler in der eingelesenen Datei");
			System.exit(1);
		}
		catch(NumberFormatException e){
			System.out.println("Keine int Werte!");
			System.exit(1);
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("kein passendes Parr gefunden! Datei Falsch formatiert");
			System.exit(1);
		}
		
		return fileGraph;
	}
	
	public void ausgeben() {
		System.out.println( "Der Graph sieht so aus: ");
		for (Node n : nodes) {
			System.out.println( "Node " + n.getId() + " - verbunden mit " + n.toString() );
		}
	}
	
	
	public void breitenSuche(int startID){
		
		LinkedList<Node> queue = new LinkedList<Node>();
		ArrayList<Node> weiﬂeknoten = new ArrayList<Node>();		//alle Weiﬂ (nicht bearbeitet
		ArrayList<Integer> distanzen = new ArrayList<Integer>();	//Liste mit Distanzen der einzelnen Knoten
		for (Node node : nodes) {
			weiﬂeknoten.add(node);
			distanzen.add(0);				//Distanz wird auf 0 gesetzt (-unendlich ist schwer zu modelieren
		}
		queue.add(nodes.get(startID));		//f¸gt Startknoten in Queue
		weiﬂeknoten.remove(startID);		//entfernt die Farbe vom Startknoten (ist jetzt Grau)
		
		while (queue.size() > 0){
			Node u = queue.get(0);			// u = erster aus Queue
			for (Edge edge : u.getAdzent()) {	//gehen alle Adeazenten von u durch
				Node v = edge.getDst();		// v = entfernung zu u
				if (weiﬂeknoten.contains(v) ){	//wenn v = weiﬂ
					weiﬂeknoten.remove(v);		//dann nicht mehr weiﬂ
					distanzen.set(nodes.indexOf(v), distanzen.get(nodes.indexOf(u)) + 1); //setzt die Distanz auf die ankunftdistanz + 1
					queue.add(v);	//f¸gt v in bearbeitungsListe ein
				}
			}
			queue.remove(0);	//das erste Element wird entfernt (u)
		}
		
		System.out.println(distanzen.toString());
	}
	
}
