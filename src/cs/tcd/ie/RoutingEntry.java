package cs.tcd.ie;

class RoutingEntry {
	int router;
	int inPort;
	int outPort;
	
	RoutingEntry(int router, int inPort, int outPort) {
		this.router = router;
		this.inPort = inPort;
		this.outPort = outPort;
		
	}
}
