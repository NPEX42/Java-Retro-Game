package np.engine.lua.gfx;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import np.engine.gfx.GraphicsEngine;

public class LuaGFX  {
	private static GraphicsEngine gfx;

	public static void setGfx(GraphicsEngine gfx) {
		LuaGFX.gfx = gfx;
	}
	
	public static class LoadSprite extends OneArgFunction {

		@Override
		public LuaValue call(LuaValue arg) {
			if(arg.istable()) {
				gfx.LoadSprite(arg.get(0).checkjstring(), arg.get(0).checkjstring());
				return LuaValue.TRUE;
			} else {
				return LuaValue.FALSE;
			}
		}
		
	}
	
}
