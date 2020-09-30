package np.engine.gfx;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import np.engine.SubSpriteMask;
import np.engine.maths.V2I;

public class GraphicsEngineImpl implements GraphicsEngine {
	public GraphicsEngineImpl(RenderWindow window) {
		this.window = window;
	}
	
	private RenderWindow window;
	private Font currentFont;
	private HashMap<String, Font> fontMap = new HashMap<>();
	private HashMap<String, Texture> spriteMap = new HashMap<>();
	private HashMap<String, SubSpriteMask> subSpriteMap = new HashMap<>();
	@Override
	public void DrawString(String text, int x, int y, int charSize, Color color) {
		if(currentFont == null) {
			System.out.println("[GFX] There Is No Font Currently Loaded...");
			return;
		} else {
			Text txt = new Text();
			txt.setFont(currentFont);
			txt.setCharacterSize(charSize);
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
		currentFont = fontMap.get(name);
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
	public void DrawFilledRect(V2I position, V2I size, Color color) {
		int x = position.x;
		int y = position.y;
		
		int width = size.x;
		int height = size.y;
		
		DrawFilledRect(x, y, width, height, color);
	}

	@Override
	public boolean LoadSprite(String path, String name) {
		Texture texture = new Texture();
		try {
			texture.loadFromFile(Paths.get(path));
			spriteMap.put(name, texture);
			System.out.println("Loaded Sprite '"+path+"' As '"+name+"'");
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
	public void DrawSprite(String name, float x, float y, int width, int height) {
		DrawSprite(name, (int)x, (int)y, width, height);
	}
	
	@Override
	public String toString() {
		return  fontMap.toString() + "--|--" + spriteMap.toString() + "--|--";
	}


}
