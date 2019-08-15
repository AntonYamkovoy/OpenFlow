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
 * End User Ex, should be able to :
 * 	Receive packet Pa(x);
 *  Send packet Pa(x); 
 */

// E1 = 50004;
// E2 = 50005;

// packet structure "OK"|"Ex" // where Ex is the string of the destination router that will be an entry in the routing tables
public class EndPoint extends Node {
	
	// default config E1 sends to r1
	
	static final int SRC_PORT = 50004;
	static final int DEFAULT_DST_PORT = 50001;
	static final String DEFAULT_DST_NODE = "localhost";	
	int destPort;
	EndPointTerminal terminal;
	InetSocketAddress dstAddress;
	
	EndPoint(EndPointTerminal terminal, String dstHost, int dstPort, int srcPort, int destination) throws Exception {
		this.terminal = terminal;
		dstAddress= new InetSocketAddress(dstHost, dstPort);
		socket= new DatagramSocket(srcPort);
		listener.go();
		
		
	}
	
	
	@Override
	public synchronized void onReceipt(DatagramPacket packet) throws IOException {
		this.notify();
		String data = getStringFromPacket(packet);
		if(data.contains("OK")) {
			terminal.textArea.setText("Packet Received");
			
		}
		
	}
	
	
	public void sendPacket() throws Exception {
		byte[] data= null;
		DatagramPacket packet= null;
		String input = "OK";
		data = input.getBytes();
		packet= new DatagramPacket(data, data.length, dstAddress);
		socket.send(packet);
	
		
	}	
	
	public synchronized void start() throws Exception {
		
		while(true) {
				this.wait();				
		}
		
		
	}
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("end point requires src and dst ports");
			return;
		}
		int endPointSrcPort = Integer.parseInt(args[0]);
		int endPointDestPort = Integer.parseInt(args[1]);
		 
		try {
			
			EndPointTerminal terminal= new EndPointTerminal();		
			EndPoint E1 = new EndPoint(terminal, DEFAULT_DST_NODE, DEFAULT_DST_PORT,endPointSrcPort, endPointDestPort);
			terminal.textField_1.setText(String.valueOf(endPointSrcPort));
			terminal.owner = E1;
			terminal.textField.setText(String.valueOf(endPointDestPort));
			E1.start();
			
		
			
				
			
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}

}
