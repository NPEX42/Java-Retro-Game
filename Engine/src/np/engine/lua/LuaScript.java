package np.engine.lua;

	import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
	import java.net.URISyntaxException;
	import java.net.URL;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;

	import org.luaj.vm2.Globals;
	import org.luaj.vm2.LuaError;
	import org.luaj.vm2.LuaValue;
	import org.luaj.vm2.Varargs;
	import org.luaj.vm2.lib.TwoArgFunction;
	import org.luaj.vm2.lib.jse.CoerceJavaToLua;
	import org.luaj.vm2.lib.jse.JsePlatform;

import np.common.core.Logger;
import np.engine.exceptions.LuaScriptException;

	/*
	 *  Lua script file loader and function executer
	 */
	public class LuaScript {

		private Logger logger = new Logger(LuaScript.class);
	    private Globals globals = JsePlatform.standardGlobals();
	    private LuaValue chunk;

	    // Script exists and is otherwise loadable
	    private boolean scriptFileExists;
	    
	    // Keep the file name, so it can be reloaded when needed
	    public String scriptFileName;
	    public Path path;
	    
	    
	    // store the returned values from function
	    public Varargs lastResults;

	    // Init the object and call the load method
	    public LuaScript(String scriptFileName) {
	    	this.scriptFileName = scriptFileName;
	    	this.path = Paths.get(scriptFileName);
	    	this.scriptFileExists = false;
	    	this.load(scriptFileName);
	    	logger.setDebug(true);
	    }
	    
	    public LuaScript() {}
	    
	    // Load the file
	    public boolean load(String scriptFileName) {
	    	logger.Debug("Loading LUA Script '",scriptFileName,"'");
	    	this.scriptFileName = scriptFileName;

	    	URL scriptFile = null;
			try {
				scriptFile = new File(scriptFileName).toURL();
			} catch (MalformedURLException e1) {
			}
	    	
	        if (scriptFile  == null) {
	        	this.scriptFileExists = false;
	            return false;
	        } else {
	        	this.scriptFileExists = true;
	        }

	        try {
	        	//chunk = globals.load(Gdx.files.internal(scriptFileName).readString());
	        	URI uri = new File(scriptFileName).toURI();
	        	Path path = Paths.get(uri);
	        	byte[] data = Files.readAllBytes(path);
	        	String scriptText = new String(data);
	        	chunk = globals.load(scriptText);
	        } catch (LuaError | IOException e) {
	        	
	        	//System.out.println(scriptFileName);
	        	
	        	if(scriptFileName.endsWith(".lua")) {
					System.out.println("Unable To Load Script '"+this.scriptFileName+"'...");
					System.out.println(e);
				}
	    		this.scriptFileExists = false;
	    		return false;
	    	}
	        
	        // An important step. Calls to script method do not work if the chunk is not called here
	        chunk.call();
	        
	        return true;
	    }
	    
	    // Load the file again
	    public boolean reload() {
	    	return this.load(this.scriptFileName);
	    }

	    // Returns true if the file was loaded correctly
	    public boolean canExecute() {
	        return scriptFileExists;
	    }

	    // Call the init function in the Lua script with the given parameters passed 
	    public boolean executeInit(Object... objects) {
	    	return executeFunction("Init", objects);
	    }
	    
	    // Call the init function in the Lua script with the given parameters passed 
	    public boolean executeUpdate(Object... objects) {
	    	return executeFunction("Update", objects);
	    }
	    
	    // Call a function in the Lua script with the given parameters passed 
	    public boolean executeFunction(String functionName, Object... objects) {
	    	return executeFunctionParamsAsArray(functionName, objects);
	    }
	    
	    // Now this function takes the parameters as an array instead, mostly meant so we can call other Lua script functions from Lua itself 
	    public boolean executeFunctionParamsAsArray(String functionName, Object[] objects) {
	        if (!canExecute()) {
	            return false;
	        }

	        LuaValue luaFunction = globals.get(functionName);
	        
	        // Check if a functions with that name exists
	        if (luaFunction.isfunction()) {
	        	LuaValue[] parameters = new LuaValue[objects.length];
	        	
	        	int i = 0;
	        	for (Object object : objects) {
	        		// Convert each parameter to a form that's usable by Lua
	        		parameters[i] = CoerceJavaToLua.coerce(object);
	        		i++;
	        	}
	        	
	        	try {
	        		// Run the function with the converted parameters
	        		lastResults = luaFunction.invoke(parameters);
	        		//System.out.println(lastResults.toString());
	        	} catch (LuaError e) {
	        		// Log the error to the console if failed
	        		
	        		logger.Exception(e);
	        		
	        		throw new LuaScriptException("Unable To Invoke Function '"+functionName+"'", scriptFileName);
	        	}
	        	return true;
	        }
	        return false;
	    }

	    // With this we register a Java function that we can call from the Lua script
	    public void registerJavaFunction(TwoArgFunction javaFunction) {
	    	globals.load(javaFunction);
	    }
	}

