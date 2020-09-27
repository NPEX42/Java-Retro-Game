package np.engine;

import java.awt.Color;

import np.engine.maths.V2F;

public interface Engine {
	public boolean ConstructWindow(int _width, int _height, String _title);
	
	public void Start(int targetFrameRate);
	public void Start(                   );
	
	public void ClearBackground(Color color);
	
	public void DrawFilledRect(int x, int y, int w, int h                             );
	public void DrawFilledRect(int x, int y, int w, int h, Color color                );
	
	@Deprecated public void DrawRect      (int x, int y, int w, int h, int thickness              );
	@Deprecated public void DrawRect      (int x, int y, int w, int h, int thickness, Color color );
	
	public void DrawString    (String text, int x, int y, int charSize, Color color );
	public void LoadFont      (String path, String name                             );
	public void SetActiveFont (String name                                          );
	
	public int ScreenWidth ();
	public int ScreenHeight();
	
	public KeyState GetKeyState  (Key key       );
	public KeyState GetMouseState(Button button );
	
	public int GetMousePosX   ();
	public int GetMousePosY   ();
	public int GetScrollDeltaX();
	public int GetScrollDeltaY();
	
	public boolean LoadSprite(String path, String name);
	public void    DrawSprite(String name, int x, int y, int w, int h);
	
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
	
	
	
}
