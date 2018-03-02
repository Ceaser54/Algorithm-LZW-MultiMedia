import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI_Lzw {
	
	public static void comp( String s ) throws IOException {
		
		String data = new String(Files.readAllBytes(Paths.get(s))) ;
		
		ArrayList<String> dec = new ArrayList<String>() ;
		
		ArrayList<Integer> c = new ArrayList<Integer>() ;
		
		for( int i=0 ; i<128 ; i++ ){
			
			char x = (char) i ;
			
			String temp = "" ;
			temp += x ;
			
			dec.add(temp) ;
		}
		
		Boolean k = false ;
		
		for( int i=0 ; i<data.length() ; i++ ){
			
			String t = "" ;
			
			int n = i , index = 0 ;
			
			do{
				k = false ;
				t += data.charAt(n) ;
				
				for( int j=0 ; j<dec.size() ; j++ ){
					
					if( t.equals(dec.get(j)) ){
						
						k = true ;
						index = j ;
						break ;
					}
				}
				n ++ ; 
				
			}while(k && n<data.length() ) ;
			
			dec.add(t) ;
			c.add(index) ;
			if( n>=data.length() && k ){
				i = n ;
			}
			else{
				
				i = n-2 ; 
			}
		}
		
		for( int i=128 ; i<dec.size() ; i++ ){
			
			System.out.println( dec.get(i) ) ;
		}
		
		for( int i=0 ; i<c.size() ; i++ ){
			
			System.out.println( c.get(i) ) ;
		}
		
		File f = new File("comp.txt");
		FileOutputStream fop = new FileOutputStream(f);
		
		for( int i=0 ; i<c.size(); i++ ){
			
			fop.write(c.get(i)) ; 
		}
		
		fop.flush();
		fop.close();
	}

	public static void decomp( String s ) throws IOException {
		
		ArrayList<Integer> c = new ArrayList<Integer>() ;
		
		InputStream input = new FileInputStream("comp.txt");

		int d = input.read() , l=0 ;
		while(d != -1) {
			
		  c.add(d) ;
		  l++ ;
		  d = input.read();
		}
		input.close() ;
		
		ArrayList<String> dec = new ArrayList<String>() ;
		
		for( int i=0 ; i<128 ; i++ ){
			
			char x = (char) i ;
			
			String temp = "" ;
			temp += x ;
			
			dec.add(temp) ;
		}
		
		String data="" , prev="" , curr="" ;
		
		for( int i=0 ; i<c.size() ; i++ ){
			
			if( i==0 ){
				
				prev = dec.get( c.get(i) ) ;
				
				data += prev ;
			}
			else{
				
				if( c.get(i)>=dec.size() ){
					
					curr = prev + prev.charAt(0) ;
				}
				else{
					
					curr = dec.get( c.get(i) ) ;
				}
			
				prev += curr.charAt(0) ;
				dec.add(prev) ;
				data += curr ;
				prev = curr ;
			}
		}
		
		PrintWriter out = new PrintWriter(s) ;
		
		out.print(data) ;
		out.flush() ;
		out.close() ;
	}

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Lzw window = new GUI_Lzw();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_Lzw() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JButton btnCompress = new JButton("Compress");
		btnCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String s = textField.getText() ;
				
				try {
					comp(s) ;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnDecompress = new JButton("Decompress");
		btnDecompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String s1 = textField_1.getText() ;
				
				try {
					decomp(s1) ;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JLabel lblWriteThePath = new JLabel("write the path of the file you want to Compress");
		lblWriteThePath.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblWriteThePath_1 = new JLabel("write the path of the file you want to Decompress in");
		lblWriteThePath_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblWriteThePath)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnCompress, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addGap(112)
							.addComponent(btnDecompress))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(lblWriteThePath_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(textField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
							.addComponent(textField_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(97, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addComponent(lblWriteThePath)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(lblWriteThePath_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCompress, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDecompress, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(34))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
