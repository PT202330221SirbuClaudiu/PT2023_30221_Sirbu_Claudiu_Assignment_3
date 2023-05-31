package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Factura;

/**
 * Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 *  Apr 03, 2017
 * http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}

	private String createSelectAllQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ");
		sb.append(type.getSimpleName());
		return sb.toString();
	}



	private String createUpdateQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET ");

		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			// Exclude static or transient fields and the ID field
			if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers()) &&
					!java.lang.reflect.Modifier.isTransient(fields[i].getModifiers()) &&
					!fields[i].getName().equals("id")) {
				sb.append(fields[i].getName());
				sb.append(" = ?");
				if (i < fields.length - 1) {
					sb.append(", ");
				}
			}
		}

		sb.append(" WHERE id = ?");
		return sb.toString();
	}

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	private String createDeleteQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " = ?");
		return sb.toString();
	}

	private String createInsertQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName());
		sb.append(" (");

		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			// Exclude static or transient fields
			if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers()) && !java.lang.reflect.Modifier.isTransient(fields[i].getModifiers())) {
				sb.append(fields[i].getName());
				if (i < fields.length - 1) {
					sb.append(", ");
				}
			}
		}

		sb.append(") VALUES (");
		for (int i = 0; i < fields.length; i++) {
			// Exclude static or transient fields
			if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers()) && !java.lang.reflect.Modifier.isTransient(fields[i].getModifiers())) {
				sb.append("?");
				if (i < fields.length - 1) {
					sb.append(", ");
				}
			}
		}

		sb.append(")");
		return sb.toString();
	}


	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectAllQuery();
		List<T> objects = new ArrayList<>();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);

			resultSet = statement.executeQuery();

			if(type.getSimpleName().compareTo("Factura")==0)
			{
				objects= (List<T>) createFacturi(resultSet);
			}
			else {
				objects = createObjects(resultSet);
			}
			return objects;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return new ArrayList<>();
	}


	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			if(type.getSimpleName().compareTo("Factura")==0)
			{
				return (T) createFacturi(resultSet).get(0);
			}
			else {
				return (T) createObjects(resultSet).get(0);
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public  T findByName(String nume) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("denumire");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, nume);
			resultSet = statement.executeQuery();

			if (type.getSimpleName().equals("Factura")) {
				return (T) createFacturi(resultSet).get(0);
			} else {
				return (T) createObjects(resultSet);
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}


	public void sterge(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createDeleteQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);

			 statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}


	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void insert(T object) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createInsertQuery();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);

			// Set the values of the statement parameters based on the object's properties
			setInsertParameters(statement, object);
			statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}

	}



	public void update(T object) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createUpdateQuery();

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);

			// Setare parametrii pentru actualizare
			setUpdateParameters(statement, object);
			System.out.println(statement);

			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}




	private void setInsertParameters(PreparedStatement statement, T object) throws SQLException {
		Field[] fields = object.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			Object value;
			try {
				value = fields[i].get(object);
			} catch (IllegalAccessException e) {
				throw new SQLException("Error accessing field: " + fields[i].getName(), e);
			}
			statement.setObject(i + 1, value);
		}
	}

	private void setUpdateParameters(PreparedStatement statement, T object) throws SQLException {
		Field[] fields = object.getClass().getDeclaredFields();
		Map<String, Integer> fieldIndexMap=null;
		if(object.getClass().getSimpleName().compareTo("Client")==0)
			fieldIndexMap = createFieldIndexMapClient(); // Mapare nume câmp - index SQL
		if(object.getClass().getSimpleName().compareTo("Produs")==0)
			fieldIndexMap = createFieldIndexMapProdus(); // Mapare nume câmp - index SQL
		if(object.getClass().getSimpleName().compareTo("Comanda")==0)
			fieldIndexMap = createFieldIndexMapComanda(); // Mapare nume câmp - index SQL

		for (Field field : fields) {
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(object);
			} catch (IllegalAccessException e) {
				throw new SQLException("Error accessing field: " + field.getName(), e);
			}

			// Verificarea existenței câmpului în mapare și setarea valorii corespunzătoare
			if (fieldIndexMap.containsKey(field.getName())) {
				int index = fieldIndexMap.get(field.getName());
				statement.setObject(index, value);
			}
		}
	}

	private Map<String, Integer> createFieldIndexMapClient() {
		Map<String, Integer> fieldIndexMap = new HashMap<>();
		fieldIndexMap.put("nume", 1);
		fieldIndexMap.put("prenume", 2);
		fieldIndexMap.put("varsta", 3);
		fieldIndexMap.put("id", 4);

		// Adaugă alte câmpuri și indexi corespunzători în mapare, dacă este necesar
		return fieldIndexMap;
	}
	private Map<String, Integer> createFieldIndexMapProdus() {
		Map<String, Integer> fieldIndexMap = new HashMap<>();
		fieldIndexMap.put("denumire", 1);
		fieldIndexMap.put("cantitate", 2);
		fieldIndexMap.put("id", 3);
		return fieldIndexMap;
	}
	private Map<String, Integer> createFieldIndexMapComanda() {
		Map<String, Integer> fieldIndexMap = new HashMap<>();
		fieldIndexMap.put("numeClient", 1);
		fieldIndexMap.put("prenumeClient", 2);
		fieldIndexMap.put("numeProdus", 3);
		fieldIndexMap.put("cantitate", 4);
		fieldIndexMap.put("id", 5);
		return fieldIndexMap;
	}
	private List<Factura> createFacturi(ResultSet resultSet) {
		List<Factura> list = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String numeClient = resultSet.getString("numeClient");
				String prenumeClient = resultSet.getString("prenumeClient");
				String denumireProdus = resultSet.getString("denumireProdus");
				Date data = resultSet.getDate("data");
				Integer cantitatea = resultSet.getInt("cantitate");

				Factura factura = new Factura(id, numeClient, prenumeClient,denumireProdus,data,cantitatea);
				list.add(factura);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
