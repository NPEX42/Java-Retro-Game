package np.engine.lua;

import java.util.HashMap;
import java.util.Map;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaVM {
	private Map<String, LuaValue> scripts = new HashMap<>();
	private Globals global = JsePlatform.standardGlobals();
	public void LoadFile(String name, String path) {
		scripts.put(name, global.loadfile(path));
	}
	
	public void RunSync(String scriptName) {
		if(scripts.containsKey(scriptName)) {
			scripts.get(scriptName).call();
		} else {
			System.out.println("[LVM/Error] Unable To Find Script '"+scriptName+"'");
		} 
	}
	
	public Thread RunAsync(String scriptName) {
		Thread t = new Thread( () -> {
			RunSync(scriptName);
		});
		t.start();
		return t;
	}
	
	public int GetInt(String scriptName, String varName) {
		return scripts.get(scriptName).get(varName).toint();
	}
	
	@Deprecated
	public void RunFunction(String scriptName, String funcName) {
		System.out.println(scripts.get(scriptName).typename());
		scripts.get(scriptName).invokemethod(funcName);
	}
}
