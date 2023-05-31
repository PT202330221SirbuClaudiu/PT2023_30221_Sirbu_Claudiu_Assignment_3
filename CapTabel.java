package start;

import java.lang.reflect.Field;

public class CapTabel {

	public static Object[] getColumnNames(Class<?> objClass) {
		Field[] fields = objClass.getDeclaredFields();
		Object[] columnNames = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			columnNames[i] = fields[i].getName();
		}
		return columnNames;
	}
}



