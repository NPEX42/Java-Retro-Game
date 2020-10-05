package np.engine.ecs;

import java.util.List;

public class Entity {
	private Scene scene;
	private int ID;
	
	public Entity(Scene scene, int i) {
		ID = i;
		this.scene = scene;
	}

	public int GetID() {
		return ID;
	}
	
	public void AddComponent(Class<? extends Component> clazz) {
		scene.AddComponent(this, clazz);
	}

	public <T extends Component> List<T> GetComponents(Class<T> clazz) {
		return scene.GetComponentsFromEntity(this, clazz);
	}
}
