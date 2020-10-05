package np.engine.ecs;

public class Component {
	protected int parentID;

	public int getParentID() {
		return parentID;
	}
	
	public void SetParentID(int id) {
		parentID = id;
	}
	
}
