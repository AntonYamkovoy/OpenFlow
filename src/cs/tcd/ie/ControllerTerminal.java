package cs.tcd.ie;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTable;



public class ControllerTerminal {
	
	JTextArea textArea;
	JTextArea textArea_1;
	ControllerTerminal(){  
		
		frameSetup();
	}
	
	
	
		public static void main(String[] args) {    
			ControllerTerminal controller=  new ControllerTerminal();   
		   
		    
		}   
		
		 
		public void frameSetup() {
			
			JFrame f=new JFrame("Controller Terminal");
			f.getContentPane().setLayout(null);
			
			JLabel lblRoutersConnected = new JLabel("Routers Connected");
			lblRoutersConnected.setBounds(20, 26, 111, 14);
			f.getContentPane().add(lblRoutersConnected);
			
			textArea = new JTextArea();
			textArea.setBounds(20, 51, 189, 52);
			f.getContentPane().add(textArea);
			
			JLabel lblRoutingTable = new JLabel("Routing Table");
			lblRoutingTable.setBounds(20, 155, 78, 14);
			f.getContentPane().add(lblRoutingTable);
			
			textArea_1 = new JTextArea();
			textArea_1.setBounds(20, 180, 355, 293);
			f.getContentPane().add(textArea_1);
			f.setSize(600,600);    
			// textArea .append to add text
			f.setVisible(true);    
			
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		}
		public void println(String s) {
			this.textArea_1.append(s);
		}
 }