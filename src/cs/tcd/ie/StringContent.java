package cs.tcd.ie;
import java.net.DatagramPacket;

import cs.tcd.ie.PacketContent;

public class StringContent implements PacketContent {
	String string;
	
	public StringContent(DatagramPacket packet) {
		byte[] data;
		
		data= packet.getData();
		string = new String(data);
	}
	
	public StringContent(String string) {
		this.string = string;
	}
	
	public String toString() {
		return string;
	}

	public DatagramPacket toDatagramPacket() {
		DatagramPacket packet= null;
		try {
			byte[] data= string.getBytes();
			packet= new DatagramPacket(data, data.length);
		}
		catch(Exception e) {e.printStackTrace();}
		return packet;
	}
}