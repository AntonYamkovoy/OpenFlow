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



public class RouterTerminal {
	Router owner;
	JTextArea textArea; 
	JTextArea textArea_1; 
	/**
	 * @wbp.parser.entryPoint
	 */
	RouterTerminal(String name){  
		
		frameSetup(name);
	}
	
		
	
		public static void main(String[] args) {    
			RouterTerminal router=  new RouterTerminal("Rx");   
			
		    
		}   
		
		
		public void frameSetup(String name) {
			
			JFrame f=new JFrame(name);
			f.getContentPane().setLayout(null);
			
			JLabel lblRoutersConnected = new JLabel("Connected to controller");
			lblRoutersConnected.setBounds(309, 33, 151, 14);
			f.getContentPane().add(lblRoutersConnected);
			
			textArea = new JTextArea();
			textArea.setBounds(309, 58, 186, 32);
			f.getContentPane().add(textArea);
			
			JLabel lblRoutingTable = new JLabel("Routing Table");
			lblRoutingTable.setBounds(20, 155, 78, 14);
			f.getContentPane().add(lblRoutingTable);
			
			textArea_1 = new JTextArea();
			textArea_1.setBounds(20, 180, 355, 293);
			f.getContentPane().add(textArea_1);
			
			JButton btnNewButton = new JButton(" Send hello to controller");
			btnNewButton.setBounds(20, 33, 198, 56);
			f.getContentPane().add(btnNewButton);
			f.setSize(600,600);    
			// textArea .append to add text
			f.setVisible(true);    
			
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			
			
			
			btnNewButton.addActionListener(new ActionListener() {
		        
				@Override
				public void actionPerformed(ActionEvent arg0) {
						
					try {
						owner.sendTableRequest();
						owner.sendHello();
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}	
						
				}          
		      });
			
		}
		
		public void println(String s) {
			this.textArea_1.append(s);
		}
 }