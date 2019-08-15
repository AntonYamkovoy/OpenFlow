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
 * Router Rx
 * Should be able to :
 *  send hello message to controller
 *  recieve hello from controller
 *  lookup routing table and send packet along the chain
 *  change it's routing table by directions from controller
 */



 // r1 = 50001;
// r2 = 50002;
// r3 = 50003;

public class Router extends Node {

	
	static final int SRC_PORT = 50001;
	static final int DEFAULT_DST_PORT = 50000;
	static final String DEFAULT_DST_NODE = "localhost";	
	int destPort;
	RouterTerminal terminal;
	InetSocketAddress dstAddress;
	RouterRoutingTable routingTable;

	Router(RouterTerminal terminal, String dstHost, int dstPort, int srcPort, String name) throws Exception {
		this.terminal = terminal;
		
		dstAddress= new InetSocketAddress(dstHost, dstPort);
		socket= new DatagramSocket(srcPort);
		listener.go();
		routingTable = new RouterRoutingTable();
		terminal.textArea.setText("false");
		
		
		
		
		
	}
	
	public void send(DatagramPacket p) throws IOException {
		socket.send(p);
	}
	
	public void sendHello() throws Exception {
		byte[] data= null; 
		DatagramPacket packet= null;
		String input = "Hello";
		data= (input +"|"+SRC_PORT).getBytes();
		packet= new DatagramPacket(data, data.length, dstAddress);
		socket.send(packet);
		
	}	
	
	public void transferPacket(DatagramPacket packet, int nextHop) throws Exception {
		byte[] data= null; 
		DatagramPacket packet2= null;
		
		data= packet.getData();
		dstAddress= new InetSocketAddress("localhost", nextHop);
		packet2= new DatagramPacket(data, data.length, dstAddress);
		socket.send(packet2);
		
	}	
	
	int getNextHop(ArrayList<routerRoutingEntry> table,int currentPort) {
		int nextHop =0;
		
		for(int i=0; i < table.size();i++) {
			
			if(table.get(i).dest==50005) {
				nextHop = table.get(i).outPort;
			}
		}
		
		
		return nextHop;
		
		/*
		if(currentPort == 50004) {
			return 50001;
		}
		else if(currentPort == 50001) {
			return 50002;
		}
		else if(currentPort == 50002) {
			return 50003;
		}
		if(currentPort == 50003) {
			return 50005;
		}
		else {
			return -1;
		}
		*/
	}
	
	
	@Override
	public synchronized void onReceipt(DatagramPacket packet) throws Exception {
		this.notify();
		String data = getStringFromPacket(packet);
		//terminal.println("packed arrived\n");
		if(data.contains("Hello")) {
			terminal.textArea.setText("true");
			return;
		}
		else if(data.contains("RoutingRequest")) {
			
			String[] content = data.split(" ");
			routerRoutingEntry entry = new routerRoutingEntry(50005,Integer.parseInt(content[2]),Integer.parseInt(content[3]));
			routerRoutingEntry entryInverse = new routerRoutingEntry(50004,Integer.parseInt(content[3]),Integer.parseInt(content[2]));
			this.routingTable.table.add(entry);
			this.routingTable.table.add(entryInverse);
			
			for(int i=0; i < this.routingTable.table.size();i++) {
				terminal.textArea_1.append("Dest: "+this.routingTable.table.get(i).dest+"  "+"In: "+this.routingTable.table.get(i).inPort+"  "+"Out: "+this.routingTable.table.get(i).outPort+"\n");
			}
		}
		else if(data.contains("OK")) {
			//meaning the data is a packet incoming from another router/ endpoint
				
				int nextHop = getNextHop(this.routingTable.table, packet.getPort());
				
				//transferPacket(packet,nextHop);
				
				packet.setPort(nextHop);
				send(packet);
				
				
			
		}
		else  {
			System.out.println("debug");
		}
		
	}
	
	public synchronized void start() throws Exception {
	
		while(true) {
				this.wait();				
		}
		
	}
	
	public void sendTableRequest() throws Exception {
		byte[] data= null; 
		DatagramPacket packet= null;
		String input = "RoutingRequest";
		data= (input +"|"+SRC_PORT).getBytes();
		packet= new DatagramPacket(data, data.length, dstAddress);
		socket.send(packet);
	}
	
	
	
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("router requires src port, and name");
			return;
		}
		int routerSrcPort = Integer.parseInt(args[0]);
		String routerName = args[1];
		
		try {
			
			RouterTerminal terminal= new RouterTerminal(routerName);		
			Router router = new Router(terminal, DEFAULT_DST_NODE, DEFAULT_DST_PORT,routerSrcPort, routerName);
			terminal.owner = router;
			
			router.start();
		
			
				
			
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}
}
