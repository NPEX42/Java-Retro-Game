package np.engine;

import np.engine.core.Engine;
import np.engine.core.Entity;

public abstract class PhysicsEntity extends Entity {
	
	private static float gravityScale = 1.0f;
	private float velocityX, velocityY;
	
	private boolean gravityAffected, isSolid, isStatic;
	public PhysicsEntity(Engine engine) {
		super(engine);
	}

	@Override
	public void Update() {
		if(gravityAffected) velocityY += PhysicsEngine.getGravity();
	}
	
	public String toString() {
		return 	  "Physics Entity {\n"
				+ "\tID: "+ID+"\n"
				+ "\tX: "+position.x+"\n"
				+ "\tY: "+position.y+"\n"
				+ "\tW: "+width+"\n"
				+ "\tH: "+height+"\n"
				+ "\tGravityAffected: "+gravityAffected+"\n"
				+ "\tIsSolid: "+isSolid+"\n"
				+ "\tStatic: "+isStatic+"\n"
				+ "}";
		
	}
	
	public void AddForce(float VX, float VY) {
		velocityX += VX;
		velocityY += VY;
	}
	
	
	
	
}
