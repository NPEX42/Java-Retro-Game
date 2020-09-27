package np.engine.maths;

public class V2F {
	public V2F(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	public float x, y;
	
	public V2F Add(V2F rhs) {
		float newX = x + rhs.x;
		float newY = y + rhs.y;
		return new V2F(newX, newY);
	}
	
	public V2F Sub(V2F rhs) {
		float newX = x - rhs.x;
		float newY = y - rhs.y;
		return new V2F(newX, newY);
	}
	
	public V2F Scale(float scale) {
		return new V2F(x * scale, y * scale);
	}
	
	public V2F Divide(float scale) {
		return new V2F(x / scale, y / scale);
	}
	
	public float MagSq() {
		return (x * x + y * y);
	}
	
	public float Mag() {
		return (float) Math.sqrt(MagSq());
	}
	
	public float Dot(V2F rhs) {
		float newX = x * rhs.x;
		float newY = y * rhs.y;
		return newX + newY;
	}
	
	public float Cross(V2F rhs) {
		float mulX = x * rhs.y;
		float mulY = y * rhs.x;
		return mulX - mulY;
	}
}
