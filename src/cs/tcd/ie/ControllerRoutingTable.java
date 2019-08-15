package cs.tcd.ie;
import java.util.ArrayList;

public class ControllerRoutingTable {
	


	int destEndPoint;
	int srcEndPoint;
	ArrayList<RoutingEntry> table;
	
	ControllerRoutingTable(int src, int dest) {
		this.destEndPoint = dest;
		this.srcEndPoint = src;
		this.table = new ArrayList<RoutingEntry>();
		
	}
	
}