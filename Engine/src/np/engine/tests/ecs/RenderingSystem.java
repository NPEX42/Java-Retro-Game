package np.engine.tests.ecs;

import java.util.List;

import np.engine.ecs.Scene;
import np.engine.ecs.SpriteRendererComponent;
import np.engine.ecs.TransformComponent;
import np.engine.gfx.GraphicsEngine;

public class RenderingSystem {
	public GraphicsEngine gfx;
	public RenderingSystem(GraphicsEngine gfx) {
		this.gfx = gfx;
	}
	
	public void Render(Scene scene) {
		for(TransformComponent transform : scene.GetComponentsFromScene(TransformComponent.class)) {
			List<SpriteRendererComponent> sprites = scene.GetComponentsById(
					SpriteRendererComponent.class, transform.getParentID()
			);
			
			for(SpriteRendererComponent sprite : sprites) {
				gfx.DrawSprite(sprite.spriteName, transform.X, transform.Y, 100, 100);
			}
		}
	}
}
