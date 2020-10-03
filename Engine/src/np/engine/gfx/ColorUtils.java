package np.engine.gfx;

import java.awt.Color;

public class ColorUtils {
	public static org.jsfml.graphics.Color AWTColorToJSFMLColor(Color color) {
		return new org.jsfml.graphics.Color(
				color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()
				);
	}
	
	

}
