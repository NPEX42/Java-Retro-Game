package np.engine;

import java.awt.Color;
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
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;

import static np.engine.KeyState.*;


public class EngineImpl implements Engine {
	RenderWindow window;
	UpdateCallback OnUpdate;
	Clock frameClock;
	
	float deltaTime = 1.0f;
	
	private HashMap<String, Texture> spriteMap = new HashMap<>();
	private HashMap<String, SubSpriteMask> subSpriteMap = new HashMap<>();
	private HashMap<String, SoundBuffer> soundMap = new HashMap<>();
	private HashMap<String, Font> fontMap = new HashMap<>();
	
	private Font currentlyActiveFont = null;
	
	private boolean[] keyStates = new boolean[65536];
	
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
		window.clear(AWTColorToJSFMLColor(color));
	}
	
	private org.jsfml.graphics.Color AWTColorToJSFMLColor(Color color) {
		return new org.jsfml.graphics.Color(
				color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()
				);
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
		rect.setFillColor(AWTColorToJSFMLColor(color));
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
		shape.setTexture(spriteMap.get(source));
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
		return Mouse.getPosition().x;
	}

	@Override
	public int GetMousePosY() {
		return Mouse.getPosition().y;
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
			txt.setColor(AWTColorToJSFMLColor(color));
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
	
	

}
