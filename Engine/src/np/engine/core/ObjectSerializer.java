package np.engine.core;

import java.lang.reflect.Field;

public class ObjectSerializer {
	public static ConfigFile Serialize(Class<?> clazz, Object obj) {
		ConfigFile file = new ConfigFile();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			try {
				field.setAccessible(true);
				String type = field.getGenericType().getTypeName();
				String name = field.getName();
				System.out.println(type);
				switch(type) {
				case "int": file.SetInt(name, field.getInt(obj)); break;
				case "short": file.SetShort(name, field.getShort(obj)); break;
				case "boolean": file.SetBool(name, field.getBoolean(obj)); break;
				case "byte": file.SetByte(name, field.getByte(obj)); break;
				case "long": file.SetLong(name, field.getInt(obj)); break;
				case "java.lang.String": break;
				}
			} catch(Exception ex) {
				file.SetString(field.getName(), "0");
			}
		}
		return file;
	}
	
	public static Object Deserialize(Class<?> clazz, ConfigFile file) {
		try {
			Object obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field : fields) {
				String name = field.getName();
				String type = field.getGenericType().getTypeName();
				switch(type) {
				case "int": field.setInt(obj, file.GetInt(name)); break;
				case "byte": field.setByte(obj, file.GetByte(name)); break;
				case "short": field.setShort(obj, file.GetShort(name)); break;
				case "boolean": field.setBoolean(obj, file.GetBool(name)); break;
				case "long": field.setLong(obj, file.GetLong(name)); break;
				}
			}
			return obj;
		} catch(Exception ex) {
			return null;
		}
	}
}
