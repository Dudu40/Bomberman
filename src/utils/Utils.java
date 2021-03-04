package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	
	String path;
	
	
	public Utils() {
		
	}
	
	public static String loadFileToString(String path) {
		// on cree le strig builder que l'on retourne
		StringBuilder builder = new StringBuilder();
		String line;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			// si l'on peut lire dans le fichier
			while((line=br.readLine())!=null) {
				// on ajoute au bluilde la ligne +\n
				builder.append(line+"\n");
			}
			
			br.close();		
		}
		catch(IOException e) {		
			e.printStackTrace();
		}
		
		return builder.toString();
	}

}
