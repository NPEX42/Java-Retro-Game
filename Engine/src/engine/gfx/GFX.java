package engine.gfx;

import java.awt.Color;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

import np.engine.gfx.GraphicsEngine;

public class GFX extends TwoArgFunction {
	private static GraphicsEngine gfx;
	
	public static void SetGfx(GraphicsEngine gfx) { GFX.gfx = gfx; }
	
	public GFX() {}
	@Override
	public LuaValue call(LuaValue modName, LuaValue env) {
		LuaValue library = tableOf();
		library.set("DrawFilledRect", new GFX.DrawFilledRect());
		env.set("GFX", library);
		return library;
	}
	
	private static class DrawFilledRect extends VarArgFunction {

		@Override
		public LuaValue call(LuaValue args) {
			
			System.out.println("[GFX/Trace] Drawing A Rectangle...");
			int X = args.arg(0).checkint();
			int Y = args.arg(1).checkint();
			
			int W = args.arg(2).checkint();
			int H = args.arg(3).checkint();
			
			int R = args.arg(4).checkint();
			int G = args.arg(5).checkint();
			int B = args.arg(6).checkint();
			
			Color color = new Color(R, G, B);
			gfx.DrawFilledRect(X, Y, W, H, color);
			return LuaValue.NIL;
		}
		
	}

}
