package engine.gfx;

import java.awt.Color;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import np.engine.gfx.GraphicsEngine;

public class GFX extends TwoArgFunction{
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
	
	private static class DrawFilledRect extends ThreeArgFunction {

		@Override
		public LuaValue call(LuaValue position, LuaValue size, LuaValue colorRGB) {
			int X = position.checkint(0);
			int Y = position.checkint(1);
			
			int W = position.checkint(0);
			int H = position.checkint(1);
			
			int R = position.checkint(0);
			int G = position.checkint(1);
			int B = position.checkint(2);
			
			Color color = new Color(R, G, B);
			gfx.DrawFilledRect(X, Y, W, H, color);
			return LuaValue.NIL;
		}
		
	}

}
