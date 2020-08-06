package kaliphobos.walruskingdom.general;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class files {
	public static void WriteToFile(String path, String text) {
		File file = new File(path);
		try {
			if (!file.createNewFile()) debug.DebugLog("File '"+path+"' already exists. Overwriting...");
			FileWriter fwriter = new FileWriter(path);
			fwriter.write(text);
			fwriter.close();
			debug.DebugLog("File has been saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String ReadFromFile(String path) {
		String result = "";
		try {
			Scanner fReader = new Scanner(new File(path));
			while (fReader.hasNextLine()) {
				result+="\n"+fReader.nextLine();
			}
			fReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

}