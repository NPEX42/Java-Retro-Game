package np.engine.core;

import java.awt.Color;

import org.jsfml.graphics.RenderWindow;

import np.engine.Button;
import np.engine.Key;
import np.engine.KeyState;
import np.engine.maths.V2F;

public interface Engine {
	public boolean ConstructWindow(int _width, int _height, String _title);
	
	public void Start(int targetFrameRate);
	public void Start(                   );
	
	//public RenderWindow GetRenderWindow();
	
	public void ClearBackground(Color color);
	
	@Deprecated(forRemoval = true) public void DrawFilledRect(int x, int y, int w, int h                             );
	@Deprecated(forRemoval = true) public void DrawFilledRect(int x, int y, int w, int h, Color color                );
	
	@Deprecated public void DrawRect      (int x, int y, int w, int h, int thickness              );
	@Deprecated public void DrawRect      (int x, int y, int w, int h, int thickness, Color color );
	
	@Deprecated(forRemoval = true) public void DrawString    (String text, int x, int y, int charSize, Color color );
	@Deprecated(forRemoval = true) public void LoadFont      (String path, String name                             );
	@Deprecated(forRemoval = true) public void SetActiveFont (String name                                          );
	
	public int ScreenWidth ();
	public int ScreenHeight();
	
	public KeyState GetKeyState  (Key key       );
	public KeyState GetMouseState(Button button );
	
	public int GetMousePosX   ();
	public int GetMousePosY   ();
	public int GetScrollDeltaX();
	public int GetScrollDeltaY();
	
	@Deprecated(forRemoval = true) public boolean LoadSprite(String path, String name);
	@Deprecated(forRemoval = true) public void    DrawSprite(String name, int x, int y, int w, int h);
	
	public void CreateSubSprite(String source, int x, int y, int w, int h, String name);
	public void DrawSubSprite(String name, int x, int y, int w, int h);
	
	public boolean LoadAudio(String path, String name);
	
	public String GetAppData();
	
	public float GetDeltaTimeSeconds();
	
	public void AddEntity(Entity entity); 
	
	
	public V2F WorldToScreen(V2F point);
	public V2F ScreenToWorld(V2F point);
	
	public void SetCameraPosition(V2F pos  );
	public void TranslateCamera  (V2F move );
	
	public void LogInfo     (String... msgs                 );
	public void LogWarn     (String... msgs                 );
	public void LogDebug    (String... msgs                 );
	public void LogFatal    (int exitCode, String... msgs   );
	public void LogFormatted(String format, Object... items );
	
	public void EnableDebug();

	@Deprecated(forRemoval = true) public void DrawSprite(String name, float x, float y, int width, int height);

	public V2F getCameraPosition();

	public V2F GetMousePos();
	
}
