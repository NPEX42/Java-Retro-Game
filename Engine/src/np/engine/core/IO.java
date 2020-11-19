package np.engine.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import np.common.exceptions.FileException;

public class IO {
	public static boolean fileExists(String path) {
		return new File(path).exists();
	}
	
	public static InputStream fileOpenForReading(String path) {
		try {
			return new FileInputStream(path);
		} catch(IOException ioex) {
			return null;
		}
	}
	
	public static OutputStream fileOpenForWriting(String path) {
		try {
			return new FileOutputStream(path);
		} catch(IOException ioex) {
			return null;
		}
	}

	public static BufferedReader OpenBufferedReader(String filePath) {
		return new BufferedReader(new InputStreamReader(fileOpenForReading(filePath)));
	}
	
	//Loads a Text file into an array of Strings, with each line its own entry, or null on failure
	public static String[] LoadStrings(String path) {
		String line;
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = OpenBufferedReader(path);
		try {
			while((line = reader.readLine()) != null) {
				buffer.append(line+"\n");
			}
			return buffer.toString().split("\n");
		} catch(Exception ex) {
			return null;
		}
	}

	public static BufferedWriter OpenBufferedWriter(String filePath) {
		return new BufferedWriter(new OutputStreamWriter(fileOpenForWriting(filePath)));
	}

	public static void CreateFile(String filePath) {
		try {
			new File(filePath).createNewFile();
		} catch (IOException e) {
			throw new FileException("Unable To Create File '"+filePath+"'...");
		}
	}
}
