package np.engine;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class SubSpriteMask {
	private final String sourceSpriteName;
	private final RectangleShape textureRect;
	public SubSpriteMask(String sourceSpriteName, int x, int y, int w, int h) {
		this.sourceSpriteName = sourceSpriteName;
		RectangleShape rect = new RectangleShape();
		rect.setPosition(x, y);
		rect.setSize(new Vector2f((float) w , (float)h));
		this.textureRect = rect;
	}
	public String getSourceSpriteName() {
		return sourceSpriteName;
	}
	public RectangleShape getTextureRect() {
		return textureRect;
	}
	
	
	
	
}
