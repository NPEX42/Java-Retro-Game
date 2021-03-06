package np.engine.ecs;

import java.util.*;

public class Scene {
	private List<Component> components = new ArrayList<>();
	private int nextID;
	public void AddComponent(Entity parent, Class<? extends Component> clazz) {
		try {
			Component c = clazz.newInstance();
			c.parentID = parent.GetID();
			components.add(c);
		} catch (Exception e) {
			System.err.println("[Scene/Fatal] Unable To Construct Component '"+clazz.getName()+"', Did You Define A No-Args Constructor?");
		}
	}
	
	public <T extends Component> List<T> GetComponentsFromEntity(Entity parent, Class<T> clazz) {
		List<T> componentsFound = new ArrayList<>();
		for(Component c : components) {
			if(c.getParentID() == parent.GetID() && c.getClass() == clazz) {
				componentsFound.add((T) c);
			}
		}
		return componentsFound;
	}
	
	public Entity NewEntity() {
		Entity ent = new Entity(this, nextID++);
		ent.AddComponent(TransformComponent.class);
		return ent;
	};
	
	@Override
	public String toString() {
		return "Components: "+components.size()+"\n"+components;
	}
	
	public <T extends Component> List<T> GetComponentsFromScene(Class<T> clazz) {
		List<T> componentsFound = new ArrayList<>();
		for(Component c : components) {
			if(c.getClass() == clazz) {
				componentsFound.add((T) c);
			}
		}
		return componentsFound;
	}
	
	public <T extends Component> List<T> GetComponentsById(Class<T> clazz, int ID) {
		List<T> componentsFound = new ArrayList<>();
		for(Component c : components) {
			if(c.getClass() == clazz && c.getParentID() == ID) {
				componentsFound.add((T) c);
			}
		}
		return componentsFound;
	}
}

