package np.game.entities;

import np.engine.core.Engine;
import np.engine.core.Entity;
import np.engine.gfx.GraphicsEngine;
import np.engine.maths.V2F;
import np.engine.maths.V2I;

public class StaticEntity extends Entity {
	private String spriteName;
	public StaticEntity(Engine engine, GraphicsEngine gfx, String name) {
		super(engine);
		this.gfx = gfx;
		this.spriteName = name;
		this.position = new V2F(0, 100);
	}

	@Override
	public void DrawSelf() {
		V2F screenPosition = engine.WorldToScreen(position);
		gfx.DrawSprite(spriteName, screenPosition.x, screenPosition.y, 100, 100);
	}

	@Override
	public void Update() {
	}

}
