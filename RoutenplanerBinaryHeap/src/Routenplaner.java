import route.items.*;

public class Routenplaner {

	// später über eingabe einlesen
	private static String START = "a";
	private static String END = "f";

	private static final Connection[] ConnectionList = { new Connection("a", "b", 7), new Connection("a", "c", 9),
			new Connection("a", "f", 14), new Connection("b", "c", 10), new Connection("b", "d", 15),
			new Connection("c", "d", 11), new Connection("c", "f", 2), new Connection("d", "e", 6),
			new Connection("e", "f", 9), };

	public static void main(String[] args) {
		RouteMap g = new RouteMap(ConnectionList);
		g.dijkstra(START);
		g.printPath(END);
	}
}
