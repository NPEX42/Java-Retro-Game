package np.engine.core;

import np.engine.maths.V2F;

@SuppressWarnings("unused")
public abstract class Entity {
	protected Engine engine;
	protected boolean isStatic, isActive, isDisplayed;
	protected V2F position;
	public int width;
	public int height;
	public long ID;
	public Entity(Engine engine) { this.engine = engine; }
	
	private static int nextID = 0;
	
	public Entity(Engine engine, boolean isStatic, int x, int y, int width, int height, long iD) {
		super();
		position = new V2F(0, 0);
		this.engine = engine;
		this.isStatic = isStatic;
		this.position.x = x;
		this.position.y = y;
		this.width = width;
		this.height = height;
		ID = iD;
	}

	public abstract void DrawSelf();
	public abstract void Update();
	
	public void SetPosition(int x, int y) {
		this.position.x = x;
		this.position.y = y;
	}
	
	public void Translate(int dx, int dy) {
		this.position.x += dx;
		this.position.y += dy;
	}
	
	public String toString() {
		return 	  "Physics Entity {\n"
				+ "\tID: "+ID+"\n"
				+ "\tX: "+position.x+"\n"
				+ "\tY: "+position.y+"\n"
				+ "\tW: "+width+"\n"
				+ "\tH: "+height+"\n"
				+ "}";
		
	}
	
	public static long GenerateRandomID() {
		return (long) (Math.random() * Long.MAX_VALUE);
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDisplayed() {
		return isDisplayed;
	}

	public void setDisplayed(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}

	public long getID() {
		return ID;
	}
	
	public String GetHexID() {
		return String.format("%016x",ID);
	}

	public V2F getPosition() {
		return position;
	}
	
	public static int GetNextID() {
		return nextID++;
	}
	
	
	
	
	
	
}
