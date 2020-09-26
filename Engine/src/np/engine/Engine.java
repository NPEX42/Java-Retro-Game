package np.engine;

import java.awt.Color;

import org.jsfml.window.Keyboard.Key;

public interface Engine {
	public boolean ConstructWindow(int _width, int _height, String _title);
	public void Start(int targetFrameRate);
	public void Start();
	
	public void ClearBackground(Color color);
	
	public void DrawRect(int x, int y, int w, int h);
	public void DrawRect(int x, int y, int w, int h, Color color);
	
	public int ScreenWidth();
	public int ScreenHeight();
	
	public KeyState GetKeyState(Key key);
	
	public boolean LoadSprite(String path, String name);
	public void DrawSprite(String name, int x, int y, int w, int h);
	
	public void CreateSubSprite(String source, int x, int y, int w, int h, String name);
	public void DrawSubSprite(String name, int x, int y, int w, int h);
	
	public boolean LoadAudio(String path, String name);
	
	public String GetAppData();
	
	
	
}
