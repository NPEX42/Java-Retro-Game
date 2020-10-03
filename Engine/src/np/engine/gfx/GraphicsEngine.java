package np.engine.gfx;

import java.awt.Color;

import np.engine.maths.V2I;

public interface GraphicsEngine {
	public void DrawString    (String text, int x, int y, int charSize, Color color );
	public void LoadFont      (String path, String name                             );
	public void SetActiveFont (String name                                          );
	
	public void DrawFilledRect(int x, int y, int w, int h                             );
	public void DrawFilledRect(int x, int y, int w, int h, Color color                );
	public void DrawFilledRect(V2I position, V2I size, Color color                    );
	
	public boolean LoadSprite(String path, String name                             );
	public void    DrawSprite(String name, int x, int y, int w, int h              );
	public void    DrawSprite(String name, float x, float y, int width, int height );
}
