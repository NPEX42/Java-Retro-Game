package np.engine.lua;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

import np.engine.exceptions.LuaScriptException;

public class LuaVM {
	private int unamedCount = 0;
	private Map<String, LuaScript> scripts = new HashMap<>();
	private Globals global = JsePlatform.standardGlobals();
	private LuaScriptManager manager = new LuaScriptManager();
	
	public LuaVM() {
		manager.Start();
	}
	
	public void LoadFile(String name, String path) {
		LuaScript script =  new LuaScript(path);
		File file = new File(path);
		if(script.canExecute()) {
			scripts.put(name, script);
			manager.Register(file.getParent());
			script.executeInit();
		} else {
			System.out.println("Unable To Load Script...");
		}
	}
	
	public Varargs RunFunction(String script, String func, Object... args) {
		
		if(!scripts.containsKey(script)) throw new LuaScriptException(new LuaScript());
		
 		if(scripts.get(script).executeFunction(func, args)) {
			return scripts.get(script).lastResults;
		} else {
			throw new LuaScriptException(scripts.get(script));
		}
	}
	
	public int LoadAll(File dir) {
		if(dir.isDirectory()) {
			File[] files = dir.listFiles(new LuaScriptAndDirFilter());
			int count = 0;
			for(File file : files) {
				if(file.isDirectory()) {
					count += LoadAll(file);
					continue;
				}
				LuaScript script =  new LuaScript(file.getAbsolutePath());
				if(script.canExecute()) {
					scripts.put(file.getAbsolutePath(), script);
					script.executeInit();
					count++;
				} else {
					if(dir.getAbsolutePath().endsWith(".lua")) {
						System.out.println("Unable To Load Script '"+file.getAbsolutePath()+"'...");
					}
				}
			}
			return count;
		} else {
			return 0;
		}
	}

	public void UpdateAll() {
		for(String key : scripts.keySet()) {
			try {
				RunFunction(key, "Update");
			} catch (LuaScriptException lscrex) {}
		}
	}
	
	public void CloseAll() {
		for(String key : scripts.keySet()) {
			try {
				RunFunction(key, "Close");
			} catch (LuaScriptException lscrex) {}
		}
	}

	public int LoadAll(String path) {
		return LoadAll(new File(path));
	}

	public void ReloadAll() {
		System.out.println("Reloading Scripts...");
		for(String key : scripts.keySet()) {
			scripts.get(key).reload();
		}
	}
	
}
