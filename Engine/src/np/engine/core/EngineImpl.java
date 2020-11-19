package np.engine.core;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.ContextActivationException;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;
import org.jsfml.window.event.Event;

import np.engine.Button;
import np.engine.Key;
import np.engine.KeyState;
import np.engine.SubSpriteMask;
import np.engine.gfx.ColorUtils;
import np.engine.maths.V2F;

import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;

import static np.engine.KeyState.*;


public class EngineImpl implements Engine {
	private ConfigFile config;
	public RenderWindow window;
	UpdateCallback OnUpdate;
	Clock frameClock;
	
	float deltaTime = 1.0f;
	
	private V2F cameraPosition = new V2F(0, 0);
	
	private HashMap<String, Texture> spriteMap = new HashMap<>();
	private HashMap<String, SubSpriteMask> subSpriteMap = new HashMap<>();
	private HashMap<String, SoundBuffer> soundMap = new HashMap<>();
	private HashMap<String, Font> fontMap = new HashMap<>();
	
	private Font currentlyActiveFont = null;
	
	private boolean[] keyStates = new boolean[65536];
	private boolean debugMode = false;
	
	private int scrollDeltaX, scrollDeltaY;
	
	private ArrayList<Entity> entities = new ArrayList<>();
	
	@Override
	public boolean ConstructWindow(int _width, int _height, String _title) {
		window = new RenderWindow(new VideoMode(_width, _height), _title);
		try {
			window.setActive();
		} catch (ContextActivationException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void Start(int targetFrameRate) {
		window.setFramerateLimit(targetFrameRate);
		frameClock = new Clock();
		while(window.isOpen()) {
			for(Event event : window.pollEvents()) {
				switch(event.type) {
				case CLOSED: window.close(); break;
				case MOUSE_WHEEL_MOVED:
					scrollDeltaX = event.asMouseWheelEvent().delta;
					scrollDeltaY = event.asMouseWheelEvent().delta;
				}
			}
			OnUpdate.call();
			
			for(Entity ent : entities) {
				if(ent.isActive()) ent.Update();
				if(ent.isDisplayed()) ent.DrawSelf();
			}
			
			window.display();
			deltaTime = frameClock.restart().asSeconds();
			
			scrollDeltaX = 0;
			scrollDeltaY = 0;
		}
	}

	@Override
	public void Start() {
		Start(1000);
	}
	
	public EngineImpl(UpdateCallback updateCB) {
		OnUpdate = updateCB;
	}

	@Override
	public void ClearBackground(Color color) {
		window.clear(ColorUtils.AWTColorToJSFMLColor(color));
	}
	
	@Override
	public void DrawFilledRect(int x, int y, int w, int h) {
		DrawFilledRect(x, y, w, h, Color.white);
	}

	@Override
	public void DrawFilledRect(int x, int y, int w, int h, Color color) {
		RectangleShape rect = new RectangleShape();
		rect.setPosition(x, y);
		rect.setSize(new Vector2f((float) w , (float)h));
		rect.setFillColor(ColorUtils.AWTColorToJSFMLColor(color));
		window.draw(rect);
	}

	@Override
	public int ScreenWidth() {
		return window.getSize().x;
	}

	@Override
	public int ScreenHeight() {
		return window.getSize().y;
	}

	@Override
	public KeyState GetKeyState(Key key) {
		return (Keyboard.isKeyPressed(Keyboard.Key.values()[key.ordinal()])) ? KEY_DOWN : KEY_UP; 
	}

	@Override
	public boolean LoadSprite(String path, String name) {
		Texture texture = new Texture();
		try {
			texture.loadFromFile(Paths.get(path));
			spriteMap.put(name, texture);
			LogDebug("Loaded Sprite '",path,"' As '",name,"'");
		} catch (IOException e) {
			System.out.println("Unable To load Sprite '"+path+"'");;
			return false;
		} 
		
		return true;
	}

	@Override
	public void DrawSprite(String name, int x, int y, int w, int h) {
		if(spriteMap.containsKey(name)) {
			RectangleShape rect = new RectangleShape();
			rect.setPosition(x, y);
			rect.setOrigin(w / 2, h / 2);
			rect.setSize(new Vector2f((float) w , (float)h));
			rect.setTexture(spriteMap.get(name));
			window.draw(rect);
		} else {
			System.err.println("Unable To Find Sprite Named '"+name+"'!");
		}
	}

	@Override
	public void CreateSubSprite(String source, int x, int y, int w, int h, String name) {
		SubSpriteMask mask = new SubSpriteMask(source, x, y, w, h);
		subSpriteMap.put(name, mask);
	}

	@Override
	public void DrawSubSprite(String name, int x, int y, int w, int h) {
		if(subSpriteMap.containsKey(name)) {
		SubSpriteMask mask = subSpriteMap.get(name);
		Texture source = spriteMap.get(mask.getSourceSpriteName());
		RectangleShape rect = mask.getTextureRect();
		RectangleShape shape = new RectangleShape();
		shape.setPosition(x, y);
		shape.setSize(new Vector2f((float) w , (float)h));
		shape.setTexture(source);
		shape.setTextureRect(rect.getTextureRect());
		window.draw(shape);
		} else {
			System.err.println("Unable To Find a SubSprite Called '"+name+"'");
		}
	}

	@Override
	public boolean LoadAudio(String path, String name) {
		SoundBuffer buffer = new SoundBuffer();
		try {
			buffer.loadFromFile(Paths.get(path));
			soundMap.put(name, buffer);
		} catch (IOException e) {
			System.out.println("Unable To load Sound '"+path+"'");
			return false;
		}
		return true;
	}

	@Override
	public String GetAppData() {
		if(OsUtils.IsWindows()) {
			return System.getProperty("APPDATA")+"\\";
		} else {
			return ".";
		}
	}

	@Override
	public int GetMousePosX() {
		return Mouse.getPosition(window).x;
	}

	@Override
	public int GetMousePosY() {
		return Mouse.getPosition(window).y;
	}

	@Override
	public KeyState GetMouseState(Button button) {
		return (Mouse.isButtonPressed(Mouse.Button.values()[button.ordinal()])) ? MOUSE_DOWN : MOUSE_UP; 
	}

	@Override
	public float GetDeltaTimeSeconds() {
		return deltaTime;
	}

	@Override
	public void DrawRect(int x, int y, int w, int h, int thickness) {
		
	}

	@Override
	public void DrawRect(int x, int y, int w, int h, int thickness, Color color) {
	}

	@Override
	public int GetScrollDeltaX() {
		return scrollDeltaX;
	}

	@Override
	public int GetScrollDeltaY() {
		return scrollDeltaY;
	}

	@Override
	public void AddEntity(Entity entity) {
		System.out.println("[ENGINE] Adding Entity #"+entity.GetHexID());
		entities.add(entity);
	}

	@Override
	public void DrawString(String text, int x, int y, int characterSize, Color color) {
		if(currentlyActiveFont == null) {
			System.out.println("[ENGINE] There Is No Font Currently Loaded...");
			return;
		} else {
			Text txt = new Text();
			txt.setFont(currentlyActiveFont);
			txt.setCharacterSize(characterSize);
			txt.setPosition(x, y);
			txt.setString(text);
			txt.setColor(ColorUtils.AWTColorToJSFMLColor(color));
			window.draw(txt);
		}
	}

	@Override
	public void LoadFont(String path, String name) {
		Font font = new Font();
		try {
			font.loadFromFile(Paths.get(path));
			fontMap.put(name, font);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void SetActiveFont(String name) {
		currentlyActiveFont = fontMap.get(name);
	}

	@Override
	public V2F WorldToScreen(V2F point) {
		return point.Sub(cameraPosition);
	}

	@Override
	public V2F ScreenToWorld(V2F point) {
		return point.Add(cameraPosition);
	}

	@Override
	public void SetCameraPosition(V2F pos) {
		cameraPosition = pos;
	}

	@Override
	public void TranslateCamera(V2F move) {
		cameraPosition.Add(move);
	}

	@Override
	public void LogInfo(String... msgs) {
		String str = "[ENGINE/Info] ";
		for (String msg : msgs) {
			str += msg;
		}
		System.out.println(str);
	}

	@Override
	public void LogWarn(String... msgs) {
		String str = "[ENGINE/Warn] ";
		for (String msg : msgs) {
			str += msg;
		}
		System.err.println(str);
	}

	@Override
	public void LogDebug(String... msgs) {
		if(!debugMode) return;
		String str = "[ENGINE/Debug] ";
		for (String msg : msgs) {
			str += msg;
		}
		System.err.println(str);
	}

	@Override
	public void LogFatal(int exitCode, String... msgs) {
		String str = "[ENGINE/Fatal] ";
		for (String msg : msgs) {
			str += msg;
		}
		System.err.println(str);
		window.close();
	}

	@Override
	public void LogFormatted(String format, Object... items) {
		System.out.printf(format, items);
	}

	@Override
	public void EnableDebug() {
		debugMode = true;
	}

	@Override
	public void DrawSprite(String name, float x, float y, int w, int h) {
		DrawSprite(name, (int)x, (int)y, w, h);
	}

	@Override
	public V2F getCameraPosition() {
		return cameraPosition;
	}

	@Override
	public V2F GetMousePos() {
		return new V2F(GetMousePosX(), GetMousePosY());
	}

	@Override
	public boolean IsKeyDown(String key) {
		return GetKeyState(Key.valueOf(key)) == KeyState.KEY_DOWN;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T GetWindow(Class<T> clazz) {
		return (T) window;
	}

	@Override
	public void LoadPersistentStore(String path) {
		if(new File(path).exists()) {
			config = new ConfigFile(path);
		} else {
			config = new ConfigFile();
		}
	}

	@Override
	public void SavePersistentStore(String path) {
		config.Save(path);
	}

	@Override
	public void SetInt(String key, int value) {
		config.SetInt(key, value);
	}

	@Override
	public void SetFloat(String key, float value) {
		config.SetFloat(key, value);
	}

	@Override
	public void SetString(String key, String value) {
		config.SetString(key, value);
	}

	@Override
	public int GetInt(String key) {
		return config.GetInt(key);
	}

	@Override
	public float GetFloat(String key) {
		return config.GetFloat(key);
	}

	@Override
	public String getString(String key) {
		return config.GetString(key);
	}

	@Override
	public void Close() {
		window.close();
	}
	
	
	
	

}
