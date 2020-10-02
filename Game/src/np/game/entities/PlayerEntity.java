package np.game.entities;

import static np.engine.KeyState.KEY_UP;

import java.awt.Color;

import np.engine.Key;
import np.engine.core.Engine;
import np.engine.core.Entity;
import np.engine.gfx.GraphicsEngine;
import np.engine.maths.V2F;

public class PlayerEntity extends Entity {
	private boolean flip = false;
	public PlayerEntity(Engine engine, GraphicsEngine gfx,  int X, int Y, int W, int H) {
		super(engine, false, X, Y, W, H, GetNextID());
		this.gfx = gfx;
		isDisplayed = true;
		isActive = true;
	}

	@Override
	public void DrawSelf() {
		V2F pos = engine.WorldToScreen(position);
		gfx.DrawSprite((flip) ? "player::right" : "player::left",pos.x,pos.y, width, height);
		gfx.DrawString("Mode: "+flip, 0, 48, 16, Color.black);
	}

	@Override
	public void Update() {
		if(engine.GetKeyState(Key.A) == KEY_UP) { position.x++; flip = true;  };
		if(engine.GetKeyState(Key.D) == KEY_UP) { position.x--; flip = false; };
		if(engine.GetKeyState(Key.S) == KEY_UP) { position.y--; }
		if(engine.GetKeyState(Key.W) == KEY_UP) { position.y++; }
	}

}
