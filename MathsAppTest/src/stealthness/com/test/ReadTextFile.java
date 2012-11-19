package stealthness.com.test;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.util.Log;

public class ReadTextFile {
	
	static public List<String> getContents(File aFile) {

		
		List contents = new ArrayList();;
         
		try {		
			Scanner scanner = new Scanner(new File("c:data1.txt"));
			while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					contents.add(line);				
			}
		} catch (FileNotFoundException e) {
			System.out.println("fail");
		} 
        
		
		
	

		return contents;
	}
}