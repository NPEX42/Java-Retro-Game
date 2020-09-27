package np.game.core;

import java.awt.Color;

import org.jsfml.window.Keyboard.Key;

import np.engine.*;
import np.game.entities.PlayerEntity;

import static np.engine.KeyState.*;

public class Game {
	int x, y;
	Engine engine = new EngineImpl(this::OnUpdate);
	
	PlayerEntity player;
	
	public void Start() {
		if(engine.ConstructWindow(720, 480, "The Adventures Of Kahn")) {
			
			if(!engine.LoadSprite("assets/tiles.png", "tileMap")) return;
			
			System.out.println(engine.GetAppData()+"TheAdventuresOfKahn");
			
			player = new PlayerEntity(engine, 100, 100, 100, 50);
			engine.AddEntity(player);
			engine.LoadFont("assets/DTM.woff", "8bit");
			engine.SetActiveFont("8bit");
			engine.Start(144);
		}
	}
	
	public void OnUpdate() {
		engine.ClearBackground(Color.BLUE);
		engine.DrawString("TEST STRING", 0, 0, 16, Color.BLACK);
	}

}
