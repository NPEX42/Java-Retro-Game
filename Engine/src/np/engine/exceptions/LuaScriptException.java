package np.engine.exceptions;

import np.engine.lua.LuaScript;

public class LuaScriptException extends RuntimeException {
	public LuaScriptException(String message, LuaScript script) {
		super("["+script.scriptFileName+"]: "+message);
	}
	
	public LuaScriptException(String message, String script) {
		super("["+script+"]: "+message);
	}
	
	public LuaScriptException(LuaScript script) {
		this("A LUA Script Exception Has Occurred when utilising File", script);
	}
}
