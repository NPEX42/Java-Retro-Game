package np.game.core;

import java.awt.Color;
import java.io.File;

import org.jsfml.graphics.RenderWindow;

import engine.gfx.*;
import np.engine.*;
import np.engine.core.Engine;
import np.engine.core.EngineImpl;
import np.engine.core.ObjectSerializer;
import np.engine.gfx.GraphicsEngine;
import np.engine.gfx.GraphicsEngineImpl;
import np.engine.lua.LuaVM;
import np.engine.maths.V2F;
import np.game.entities.ConfigFile;
import np.game.entities.PlayerEntity;
import np.game.entities.StaticEntity;

import static np.engine.KeyState.*;

public class Game {
	int x, y;
	String persistent = "assets/store.txt";
	
	Engine engine = new EngineImpl(this::OnUpdate);
	GraphicsEngine gfx;
	
	
	ConfigFile config = new ConfigFile("assets/config.txt");
	
	PlayerEntity player;
	StaticEntity block;
	
	LuaVM vm = new LuaVM();
	public void Start() {
		if(engine.ConstructWindow(720, 480, "The Adventures Of Kahn")) {
			gfx = new GraphicsEngineImpl(engine.GetWindow(RenderWindow.class));
			
			ENGINE.SetGfx(gfx);
			ENGINE.SetEngine(engine);
			
			engine.LogDebug("Loading Fonts...");
			gfx.LoadFont("assets/DTM.woff", "8bit");
			gfx.SetActiveFont("8bit");
			
			engine.LoadPersistentStore(persistent);
			
			engine.LogInfo("Starting Game...");
			System.out.println("Loaded "+vm.LoadAll("assets/")+" LUA Scripts...");
			engine.Start(144);
			
			vm.CloseAll();
			
			engine.SavePersistentStore(persistent);
		}
	}
	
	public void OnUpdate() {
		engine.ClearBackground(new Color(0,100,50));
		vm.UpdateAll();
	}

}
