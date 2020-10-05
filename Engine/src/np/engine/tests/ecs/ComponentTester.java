package np.engine.tests.ecs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import np.engine.ecs.Entity;
import np.engine.ecs.Scene;
import np.engine.ecs.TransformComponent;

class ComponentTester {
	
	@Test
	void testComponent() {
		Scene scene = new Scene();
		Entity entity = scene.NewEntity();
		System.out.println(scene);
		scene.NewEntity();
		System.out.println(scene);
		List<TransformComponent> transforms = entity.GetComponents(TransformComponent.class);
		for(TransformComponent tc : transforms) {
			System.out.println(tc);
		}
		
	}

}
