package np.game.entities;

import static np.engine.KeyState.KEY_UP;

import np.engine.Engine;
import np.engine.Entity;
import np.engine.Key;

public class PlayerEntity extends Entity {
	
	public PlayerEntity(Engine engine, int X, int Y, int W, int H) {
		super(engine, false, X, Y, W, H, GenerateRandomID());
		isDisplayed = true;
		isActive = true;
	}

	@Override
	public void DrawSelf() {
		engine.DrawSprite("tileMap",x,y,width,height);
	}

	@Override
	public void Update() {
		if(engine.GetKeyState(Key.A) == KEY_UP) x++;
		if(engine.GetKeyState(Key.D) == KEY_UP) x--;
		if(engine.GetKeyState(Key.S) == KEY_UP) y--;
		if(engine.GetKeyState(Key.W) == KEY_UP) y++;
		
		if(engine.GetKeyState(Key.LEFT) == KEY_UP) x++;
		if(engine.GetKeyState(Key.RIGHT) == KEY_UP) x--;
		if(engine.GetKeyState(Key.DOWN) == KEY_UP) y--;
		if(engine.GetKeyState(Key.UP) == KEY_UP) y++;
	}

}
