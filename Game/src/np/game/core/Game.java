package np.game.core;

import java.awt.Color;


import np.engine.*;
import np.engine.core.Engine;
import np.engine.core.EngineImpl;
import np.engine.core.ObjectSerializer;
import np.engine.gfx.GraphicsEngine;
import np.engine.gfx.GraphicsEngineImpl;
import np.engine.maths.V2F;
import np.game.entities.ConfigFile;
import np.game.entities.PlayerEntity;
import np.game.entities.StaticEntity;

import static np.engine.KeyState.*;

public class Game {
	int x, y;
	
	int i = 17298;
	short s;
	boolean bool;
	byte b;
	long l;
	String title;
	Engine engine = new EngineImpl(this::OnUpdate);
	GraphicsEngine gfx;
	
	
	ConfigFile config = new ConfigFile("assets/config.txt");
	
	PlayerEntity player;
	StaticEntity block;
	public void Start() {
		if(engine.ConstructWindow(720, 480, "The Adventures Of Kahn")) {
			
			gfx = new GraphicsEngineImpl(((EngineImpl)engine).window);
			
			block = new StaticEntity(engine, gfx, "player::left");
			
			engine.EnableDebug();
			engine.LogDebug("Loading Sprites...");
			if(!gfx.LoadSprite("assets/player_right.png", "player::right")) return;
			if(!gfx.LoadSprite("assets/player_left.png", "player::left")) return;
			if(!gfx.LoadSprite("assets/c64-Tiles.png", "c64::tiles")) return;
			
			System.out.println(engine.GetAppData()+"TheAdventuresOfKahn");
			
			player = new PlayerEntity(engine, gfx, config.GetInt("PlayerX"), config.GetInt("PlayerY"), 100, 100);
			engine.AddEntity(player);
			engine.AddEntity(block);
			engine.LogDebug("Loading Fonts...");
			gfx.LoadFont("assets/DTM.woff", "8bit");
			gfx.SetActiveFont("8bit");
			
			engine.LogInfo("Starting Game...");
			
			config.SetString("TEST", "This Is A Test");
			
			System.out.println(config.GetString("TEST"));
			
			System.out.println(gfx);
			
			engine.Start(144);
			
		
			ObjectSerializer.Serialize(Game.class, this).Save("assets/game.txt");
			
			config.SetInt("PlayerX",(int) player.getPosition().x);
			config.SetInt("PlayerY",(int) player.getPosition().y);
			config.Save("assets/config.txt");
		}
	}
	
	public void OnUpdate() {
		engine.ClearBackground(Color.GREEN);
		gfx.DrawString("Player Pos: "+player.getPosition(), 0, 0, 16, Color.BLACK);
		gfx.DrawString("Camera Position: "+engine.getCameraPosition(), 0, 16, 16, Color.BLACK);
		gfx.DrawString("Mouse Position: "+engine.GetMousePos(),0,32,16, Color.black);
		engine.SetCameraPosition(player.getPosition().Sub(new V2F(engine.ScreenWidth() / 2, engine.ScreenHeight() / 2)));
	}

}
