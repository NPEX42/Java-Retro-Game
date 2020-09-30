package np.engine.maths;

public class V2I {
	public V2I(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int x, y;
	
	public V2I Add(V2I rhs) {
		int newX = x + rhs.x;
		int newY = y + rhs.y;
		return new V2I(newX, newY);
	}
	
	public V2I Sub(V2I rhs) {
		int newX = x - rhs.x;
		int newY = y - rhs.y;
		return new V2I(newX, newY);
	}
	
	public V2I Scale(int scale) {
		return new V2I(x * scale, y * scale);
	}
	
	public V2I Divide(int scale) {
		return new V2I(x / scale, y / scale);
	}
	
	public int MagSq() {
		return (x * x + y * y);
	}
	
	public int Mag() {
		return (int) Math.sqrt(MagSq());
	}
	
	public int Dot(V2I rhs) {
		int newX = x * rhs.x;
		int newY = y * rhs.y;
		return newX + newY;
	}
	
	public int Cross(V2I rhs) {
		int mulX = x * rhs.y;
		int mulY = y * rhs.x;
		return mulX - mulY;
	}
	
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
