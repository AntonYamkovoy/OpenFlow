package cs.tcd.ie;


import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


import tcdIO.*;

/* 
 * Controller C v1
 * Should be able to :
 *  send hello message to routers;
 *  receive hello message back from routers;
 *  lookup directions in hardcoded routing table for topology
 *  tell routers to change their routing tables
 */

/* 
 * Controller C v2
 * Should be able to :
 *  receive hello from router
 *  send hello back to routers
 *  set up routing table for routers using LinkState / DistanceVector routing
 *  tell routers to change their routing tables
 */



public class Controller extends Node {
	
	static final int CONTROLLER_PORT = 50000;
	static final String DEFAULT_DST_NODE = "localhost";	
	ControllerTerminal terminal;
	InetSocketAddress dstAddress;
	static int routerPort;
	ArrayList<Integer> routersList;
	ControllerRoutingTable routingTable;
	
	
	Controller(ControllerTerminal terminal, String dstHost, int srcPort, int dstPort) throws SocketException {
		this.terminal = terminal;
		dstAddress= new InetSocketAddress(dstHost, dstPort);
		socket= new DatagramSocket(srcPort);
		listener.go();
		routersList = new ArrayList<Integer>();
		routingTable = new ControllerRoutingTable(50004,50005);
		routingConfig();
		
		
		
		
	}
	

	
	void routingConfig() {
		int src = routingTable.srcEndPoint;
		int dst = routingTable.destEndPoint;
		terminal.textArea_1.append("destination ="+dst+"\n");
		terminal.textArea_1.append("source ="+src+"\n");
		RoutingEntry entryR1 = new RoutingEntry(50001,50004,50002); // router | in | out
		RoutingEntry entryR2 = new RoutingEntry(50002,50001,50003);
		RoutingEntry entryR3 = new RoutingEntry(50003,50002,50005);
		terminal.textArea_1.append("Router: "+entryR1.router+"  "+"In: "+entryR1.inPort+"  "+"Out: "+entryR1.outPort+"\n");
		terminal.textArea_1.append("Router: "+entryR2.router+"  "+"In: "+entryR2.inPort+"  "+"Out: "+entryR2.outPort+"\n");
		terminal.textArea_1.append("Router: "+entryR3.router+"  "+"In: "+entryR3.inPort+"  "+"Out: "+entryR3.outPort+"\n");
	
		routingTable.table.add(entryR1);
		routingTable.table.add(entryR2);
		routingTable.table.add(entryR3);
		
		
		
	}
	
	
	
	
	@Override
	public synchronized void onReceipt(DatagramPacket packet) throws Throwable {
		this.notify();
		String data = getStringFromPacket(packet);
		String[] content = data.split("//|");
		
		if(data.contains("Hello")) {
			if(this.routersList.contains(packet.getPort())) {
				return;
			}
			String text = terminal.textArea.getText();
			
			terminal.textArea.setText(text+packet.getPort()+" ");
			dstAddress= new InetSocketAddress("localhost", packet.getPort());
				sendHelloBack(packet.getPort());
				routersList.add(packet.getPort());

		}
		else if(data.contains("RoutingRequest")) {
			
			int routerPort = packet.getPort();
			if(this.routersList.contains(routerPort)) {
				return;
			}
			dstAddress= new InetSocketAddress("localhost", packet.getPort());
			
			for(int i=0; i < this.routingTable.table.size(); i++) {
				if(this.routingTable.table.get(i).router == routerPort) {
					RoutingEntry r = this.routingTable.table.get(i);
					sendRoutingBack(routerPort,r);
				}
			}
			
			
		}
		
		
	}
	
	
	
	public void sendRoutingBack(int routerPort, RoutingEntry entry) throws Exception {
		byte[] data= null;
		DatagramPacket packet= null;
		data =("RoutingRequest"+" "+ entry.router+" "+entry.inPort+" "+entry.outPort).getBytes();
		
		packet= new DatagramPacket(data, data.length,dstAddress );
		socket.send(packet);
	}
	
	public void sendHelloBack(int routerPort) throws Exception {
		byte[] data= null;
		DatagramPacket packet= null;
		data = "Hello".getBytes();
		
		packet= new DatagramPacket(data, data.length,dstAddress );
		socket.send(packet);
	}	
	
	public synchronized void start() throws Exception {
		
		while(true) {
				this.wait();				
		}
		
	}
	
	public static void main(String[] args) {
		
		
		
		
		
		try {
			
			ControllerTerminal terminal = new ControllerTerminal();		
			Controller controller = new Controller(terminal, DEFAULT_DST_NODE,CONTROLLER_PORT,routerPort);
			
			controller.start();
		
			
				
			
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}

}
