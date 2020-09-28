package np.game.core;

import java.awt.Color;

import org.jsfml.window.Keyboard.Key;

import np.engine.*;
import np.game.entities.CameraController;
import np.game.entities.PlayerEntity;

import static np.engine.KeyState.*;

public class Game {
	int x, y;
	Engine engine = new EngineImpl(this::OnUpdate);
	
	PlayerEntity player;
	CameraController cam;
	
	public void Start() {
		if(engine.ConstructWindow(720, 480, "The Adventures Of Kahn")) {
			engine.EnableDebug();
			engine.LogDebug("Loading Sprites...");
			if(!engine.LoadSprite("assets/player.png", "tileMap")) return;
			
			System.out.println(engine.GetAppData()+"TheAdventuresOfKahn");
			
			player = new PlayerEntity(engine, 100, 100, 100, 50);
			cam = new CameraController(engine);
			engine.AddEntity(player);
			engine.AddEntity(cam);
			engine.LogDebug("Loading Fonts...");
			engine.LoadFont("assets/DTM.woff", "8bit");
			engine.SetActiveFont("8bit");
			
			engine.LogInfo("Starting Game...");
			
			engine.Start(144);
		}
	}
	
	public void OnUpdate() {
		engine.ClearBackground(Color.BLUE);
		engine.DrawString("Player Pos: "+player.getPosition(), 0, 0, 16, Color.BLACK);
	}

}
