package np.engine.lua;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import static java.nio.file.StandardWatchEventKinds.*;

import np.asio.core.FileWatcher;
import np.common.core.Logger;
import np.common.exceptions.FileException;
import np.common.exceptions.ThreadException;
import np.common.exceptions.WatchableException;

public class LuaScriptManager extends FileWatcher {
	private HashMap<Path,LuaScript> scripts = new HashMap<>();
	private Logger logger = new Logger(LuaScriptManager.class);
	
	public void AddScript(LuaScript script) {
		scripts.put(script.path, script);
	}

	@Override
	public void OnFileCreated(File file, Path path) {
	}

	@Override
	public void OnFileEdited(File file, Path path) {
		scripts.get(path).reload();
	}

	@Override
	public void OnFileDeleted(File file, Path path) {
		// TODO Auto-generated method stub
		
	}
	
}
