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
			System.out.println("[ConfigFile/Info] Saving Config To '"+filePath+"'");
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
	
	public int   GetInt  (String key) { return Integer.parseInt(GetString(key)); }
	public byte  GetByte (String key) { return Byte.parseByte  (GetString(key)); }
	public short GetShort(String key) { return Short.parseShort(GetString(key)); }
	
	public boolean GetBool(String key) {
		return GetInt(key) == 1 ? true : false;
	}
	
	public void SetInt(String key, int value) {
		SetString(key, ""+value);
	}
	
	public void SetByte(String key, byte value) {
		SetString(key, ""+value);
	}
	
	public void SetShort(String key, short value) {
		SetString(key, ""+value);
	}
	
	public void SetBool(String key, boolean value) {
		SetString(key, (value) ? "1" : "0");
	}

}
