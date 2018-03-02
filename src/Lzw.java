import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Lzw {
	
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
	
	public static void main(String[] args) throws IOException {
		
		String p1 , p2 ;
		
		Scanner input1 = new Scanner(System.in);
		p1 = input1.next();
		
		
		comp(p1) ;
		
		Scanner input2 = new Scanner(System.in);
		p2 = input2.next();
		
		decomp(p2) ;
	}

}
