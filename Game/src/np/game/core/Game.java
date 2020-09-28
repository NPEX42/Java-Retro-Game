package np.game.core;

import java.awt.Color;


import np.engine.*;
import np.engine.maths.V2F;
import np.game.entities.PlayerEntity;

import static np.engine.KeyState.*;

public class Game {
	int x, y;
	Engine engine = new EngineImpl(this::OnUpdate);
	
	PlayerEntity player;
	public void Start() {
		if(engine.ConstructWindow(720, 480, "The Adventures Of Kahn")) {
			engine.EnableDebug();
			engine.LogDebug("Loading Sprites...");
			if(!engine.LoadSprite("assets/player_right.png", "player::right")) return;
			if(!engine.LoadSprite("assets/player_left.png", "player::left")) return;
			if(!engine.LoadSprite("assets/c64-Tiles.png", "c64::tiles")) return;
			
			engine.CreateSubSprite("c64::tiles", 0 , 0, 16, 16, "Tiles::Tree");
			engine.CreateSubSprite("c64::tiles", 16, 0, 16, 16, "Tiles::Chest");
			engine.CreateSubSprite("c64::tiles", 48, 0, 16, 16, "Tiles::Coin");
			engine.CreateSubSprite("c64::tiles", 64, 0, 16, 16, "Tiles::Fire");
			
			System.out.println(engine.GetAppData()+"TheAdventuresOfKahn");
			
			player = new PlayerEntity(engine, 100, 100, 100, 100);
			engine.AddEntity(player);
			engine.LogDebug("Loading Fonts...");
			engine.LoadFont("assets/DTM.woff", "8bit");
			engine.SetActiveFont("8bit");
			
			engine.LogInfo("Starting Game...");
			
			engine.Start(144);
		}
	}
	
	public void OnUpdate() {
		engine.ClearBackground(Color.GREEN);
		engine.DrawString("Player Pos: "+player.getPosition(), 0, 0, 16, Color.BLACK);
		engine.DrawString("Camera Position: "+engine.getCameraPosition(), 0, 16, 16, Color.BLACK);
		engine.DrawString("Mouse Position: "+engine.GetMousePos(),0,32,16, Color.black);
		
		engine.DrawSubSprite("Tiles::Tree", 0, 0, 64, 64);
		//engine.SetCameraPosition(player.getPosition().Sub(new V2F(100, 100)));
	}

}
