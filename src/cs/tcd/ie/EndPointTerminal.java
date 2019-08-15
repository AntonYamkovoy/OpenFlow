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



public class EndPointTerminal {
	 EndPoint owner;
	JTextArea textArea;
	 JTextField textField;
	JTextField textField_1;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	EndPointTerminal(){  
		
		frameSetup();
	}
		
	
	
		public static void main(String[] args) {    
			//EndPointTerminal endPoint=  new EndPointTerminal();   
		   
		    
		}   
		
		
		public void frameSetup() {
			
			JFrame f=new JFrame("EndPoint Terminal"); 
			JButton sendButton=new JButton("Send Packet"); 
			
			
			sendButton.setBounds(20,50,111,52);
			f.getContentPane().add(sendButton);
			f.getContentPane().setLayout(null);
			
			JLabel lblReceivedPacket = new JLabel("Received packet?");
			lblReceivedPacket.setBounds(20, 132, 111, 14);
			f.getContentPane().add(lblReceivedPacket);
			
			textArea = new JTextArea();
			textArea.setBounds(20, 156, 86, 22);
			f.getContentPane().add(textArea);
			
			JLabel lblSrcendpoint = new JLabel("srcEndPoint");
			lblSrcendpoint.setBounds(189, 50, 85, 14);
			f.getContentPane().add(lblSrcendpoint);
			
			JLabel lblDstendpoint = new JLabel("dstEndPoint");
			lblDstendpoint.setBounds(192, 132, 92, 14);
			f.getContentPane().add(lblDstendpoint);
			
			textField = new JTextField();
			textField.setBounds(175, 158, 86, 20);
			f.getContentPane().add(textField);
			textField.setColumns(10);
			
			textField_1 = new JTextField();
			textField_1.setBounds(175, 66, 86, 20);
			f.getContentPane().add(textField_1);
			textField_1.setColumns(10);
			f.setSize(300,300);    
			// textArea .append to add text
			f.setVisible(true);    
			
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
			
			
			
			
			
			
			sendButton.addActionListener(new ActionListener() {
		        
				@Override
				public void actionPerformed(ActionEvent arg0) {
						
					try {
						owner.sendPacket();
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}	
						
				}          
		      });
			
		}
 }