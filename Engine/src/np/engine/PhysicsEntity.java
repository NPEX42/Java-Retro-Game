package np.engine;

public abstract class PhysicsEntity extends Entity {
	private boolean gravityAffected, isSolid, isStatic;
	public PhysicsEntity(Engine engine) {
		super(engine);
	}

	@Override
	public void Update() {
		
	}
	
	public String toString() {
		return 	  "Physics Entity {\n"
				+ "\tID: "+ID+"\n"
				+ "\tX: "+x+"\n"
				+ "\tY: "+y+"\n"
				+ "\tW: "+width+"\n"
				+ "\tH: "+height+"\n"
				+ "\tGravityAffected: "+gravityAffected+"\n"
				+ "\tIsSolid: "+isSolid+"\n"
				+ "\tStatic: "+isStatic+"\n"
				+ "}";
		
	}
	
	
}
