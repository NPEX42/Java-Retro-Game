package np.engine;

public abstract class Entity {
	protected Engine engine;
	public Entity(Engine engine) { this.engine = engine; }
	public abstract void DrawSelf();
	public abstract void Update();
}
