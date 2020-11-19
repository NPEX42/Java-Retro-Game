package engine.gfx;

import java.awt.Color;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import np.engine.core.Engine;
import np.engine.gfx.GraphicsEngine;

public class ENGINE extends TwoArgFunction {

	private static GraphicsEngine gfx;
	private static Engine engine;
	
	public static void SetGfx(GraphicsEngine gfx) { ENGINE.gfx = gfx; }
	public static void SetEngine(Engine engine) { ENGINE.engine = engine; }
	
	public ENGINE() {}
	@Override
	public LuaValue call(LuaValue modName, LuaValue env) {
		LuaValue library = tableOf();
		
		LuaValue graphics = tableOf();
		LuaValue input = tableOf();
		LuaValue core = tableOf();
		
		graphics.set("DrawFilledRect", new ENGINE.DrawFilledRect());
		graphics.set("DrawString", new ENGINE.DrawString());
		graphics.set("DrawImage", new DrawImage());
		graphics.set("LoadImage", new LoadImage());
		
		input.set("IsKeyDown", new ENGINE.IsKeyDown());
		input.set("GetMousePosX", new GetMousePosX());
		input.set("GetMousePosY", new GetMousePosY());
		
		LuaValue storeFuncs = tableOf();
		
		storeFuncs.set("SetInt", new SetInt());
		storeFuncs.set("GetInt", new GetInt());
		storeFuncs.set("GetString", new GetString());
		storeFuncs.set("SetString", new SetString());
		storeFuncs.set("SetTable", new SetTable());
		
		core.set("Close", new RequestClose());
		
		library.set("storage", storeFuncs);
		library.set("gfx", graphics);
		library.set("input", input);
		library.set("core", core);
		
		
		env.set("Engine", library);
		return library;
	}
	
	public static LuaValue NIL() {
		return LuaValue.NIL;
	} 
	
	public static LuaValue toLuaValue(boolean state) {
		return (state) ? LuaValue.TRUE : LuaValue.FALSE;
	}
	
	public static LuaValue toLuaValue(int value) {
		return LuaInteger.valueOf(value);
	}
	
	public static LuaValue toLuaValue(float value) {
		return LuaNumber.valueOf(value);
	}
	
	public static LuaValue toLuaValue(String value) {
		return LuaString.valueOf(value);
	}
	
	private static class DrawFilledRect extends VarArgFunction {

		@Override
		public Varargs invoke(Varargs args) { 
			int X = args.arg(1).checkint();
			int Y = args.arg(2).checkint();
			
			int W = args.arg(3).checkint();
			int H = args.arg(4).checkint();
			
			int R = args.arg(5).checkint();
			int G = args.arg(6).checkint();
			int B = args.arg(7).checkint();
			
			Color color = new Color(R, G, B);
			gfx.DrawFilledRect(X, Y, W, H, color);
			return LuaValue.NIL;
		}
		
	}
	
	private static class DrawString extends VarArgFunction {
		
		@Override
		public Varargs invoke(Varargs args) {
			if(args.narg() < 7)  throw new LuaError(
					"[Arity Error]: DrawString Expects at least 7 Args, (X, Y, SIZE, R, G, B, Text)..."
			);
			
			int X = args.arg(1).checkint();
			int Y = args.arg(2).checkint();
			
			int SIZE = args.checkint(3);
			
			int R = args.arg(4).checkint();
			int G = args.arg(5).checkint();
			int B = args.arg(6).checkint();
			
			String text = args.checkjstring(7);
			Color color = new Color(R, G, B);
			gfx.DrawString(text, X, Y, SIZE, color);
			
			return LuaValue.NIL;
		}
	}
	
	private static class IsKeyDown extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			return (engine.IsKeyDown(args.checkjstring(1)) ? LuaValue.TRUE : LuaValue.FALSE);
		}
	}
	
	private static class LoadImage extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			if(args.narg() < 2)  throw new LuaError("[Arity Error]: LoadImage Expects at least 2 Args...");
			
			boolean result = gfx.LoadSprite(args.checkjstring(1), args.checkjstring(2));
			return toLuaValue(result);
		}
	}
	
	private static class DrawImage extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			if(args.narg() < 5)  throw new LuaError("[Arity Error]: DrawImage Expects at least 5 Args...");
			gfx.DrawSprite(
					args.checkjstring(1),
					args.checkint(2),
					args.checkint(3),
					args.checkint(4),
					args.checkint(5)
			);
			return LuaValue.NIL;
		}
	}
	
	private static class SetInt extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			engine.SetInt(args.checkjstring(1), args.checkint(2));
			return NIL();
		}
	}
	
	private static class GetInt extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs arg0) {
			return toLuaValue(engine.GetInt(arg0.checkjstring(1)));
		}
	}
	
	private static class GetString extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs arg0) {
			return toLuaValue(engine.getString(arg0.checkjstring(1)));
		}
	}
	
	private static class SetString extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs arg0) {
			String key = arg0.checkjstring(1);
			String value = arg0.checkjstring(2);
			engine.SetString(key, value);
			return NIL();
		}
	}
	
	private static class SetTable extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs arg0) {
			String parent = arg0.checkjstring(1);
			LuaTable table = arg0.checktable(2);
			SaveTable(table, 8, parent);
			return NIL();
		}
	}

	private static void SaveTable(LuaTable table, int depth, String prefix) {
		if(depth == 0) return;
		int argCount = table.length();
		System.out.println(argCount);
		for(int i = 1; i <= argCount; i++) {
				String key = prefix+"."+i;
				
				LuaValue value = table.get(i);
				
				System.out.println(key+": "+value);
				if(value.isnumber()) { engine.SetFloat(key, value.checknumber().tofloat()); }
				if(value.isstring()) { engine.SetString(key, value.checkjstring()); }
				if(value.istable()) { SaveTable(value.checktable(), depth - 1, key); }
		}
	}
	
	private static class GetMousePosX extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs arg0) {
			return toLuaValue(engine.GetMousePosX());
		}
	}
	
	private static class GetMousePosY extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs arg0) {
			return toLuaValue(engine.GetMousePosY());
		}
	}
	
	private static class RequestClose extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs arg0) {
			engine.Close();
			return NIL();
		}
	}
	
	private static class DeltaTime extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs args) {
			return toLuaValue(engine.GetDeltaTimeSeconds());
		}
	}
}
	
