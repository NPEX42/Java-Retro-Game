package np.game.core;

import java.awt.Color;

import org.jsfml.window.Keyboard.Key;

import np.engine.*;
import static np.engine.KeyState.*;

public class Game {
	int x, y;
	Engine engine = new EngineImpl(this::OnUpdate);
	public void Start() {
		if(engine.ConstructWindow(720, 480, "The Adventures Of Kahn")) {
			
			if(!engine.LoadSprite("assets/player.png", "tileMap")) return;
			
			System.out.println(engine.GetAppData()+"TheAdventuresOfKahn");
			
			engine.Start(144);
		}
	}
	
	public void OnUpdate() {
		engine.ClearBackground(Color.BLUE);
		engine.DrawSprite("tileMap",x,y,100,100);
		
		//engine.DrawSubSprite("road_0", 0, 0, 100, 100);
		if(engine.GetKeyState(Key.A) == KEY_UP) x++;
		if(engine.GetKeyState(Key.D) == KEY_UP) x--;
		if(engine.GetKeyState(Key.S) == KEY_UP) y--;
		if(engine.GetKeyState(Key.W) == KEY_UP) y++;
	}

}
