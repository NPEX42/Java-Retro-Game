package np.game.entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

public class ConfigFile {
	private HashMap<String, String> config = new HashMap<>();
	
	public ConfigFile() {}
	public ConfigFile(String filePath) {
		if(IO.fileExists(filePath)) {
			String[] fileContents = IO.LoadStrings(filePath);
			for(String line : fileContents) {
				
				String[] sections = line.split(":");
				
				if(sections.length < 2) {
					System.out.println("Line '"+line+"' Is Malformatted...");
				} else {
					config.put(sections[0], sections[1]);
				}
				
			}
		} else {
			System.out.println("Failed To Load Config '"+filePath+"'...");
		}
	}
	
	public boolean Save(String filePath) {
		BufferedWriter writer = IO.OpenBufferedWriter(filePath);
		try {
			for(String key : config.keySet()) {
				writer.write(key+":"+config.get(key));
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch(IOException ioex) {
			return false;
		}
		return true;
	}
 	
	@Override
	public int hashCode() {
		return config.hashCode();
	}
	
	@Override
	public String toString() {
		return config.toString();
	}
	
	public String GetString(String key) {
		if(config.containsKey(key)) {
			return config.getOrDefault(key, "0");
		} else {
			config.put(key, "0");
			return "0";
		}
	}
	
	public void SetString(String key, String value) {
		config.put(key, value);
	}

}
