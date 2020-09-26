package np.engine;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.ContextActivationException;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;
import org.jsfml.window.event.Event;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

import static np.engine.KeyState.*;


public class EngineImpl implements Engine {
	RenderWindow window;
	UpdateCallback OnUpdate;
	
	private HashMap<String, Texture> spriteMap = new HashMap<>();
	private HashMap<String, SubSpriteMask> subSpriteMap = new HashMap<>();
	private HashMap<String, SoundBuffer> soundMap = new HashMap<>();
	
	private boolean[] keyStates = new boolean[65536];
	
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
		while(window.isOpen()) {
			for(Event event : window.pollEvents()) {
				switch(event.type) {
				case CLOSED: window.close(); break;
				case KEY_PRESSED: keyStates[event.asKeyEvent().key.ordinal()] = true; break;
				case KEY_RELEASED: keyStates[event.asKeyEvent().key.ordinal()] = false; System.out.println(event.asKeyEvent().key); break;
				}
			}
			OnUpdate.call();
			window.display();
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
	public void DrawRect(int x, int y, int w, int h) {
		DrawRect(x, y, w, h, Color.white);
	}

	@Override
	public void DrawRect(int x, int y, int w, int h, Color color) {
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
		return (Keyboard.isKeyPressed(key)) ? KEY_DOWN : KEY_UP; 
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
	
	

}
