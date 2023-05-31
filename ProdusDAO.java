package dao;

import connection.ConnectionFactory;
import model.Produs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class ProdusDAO  extends AbstractDAO<Produs> {

    public static Produs findByDenumire(String denumire) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM produs WHERE denumire = ?";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, denumire);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("denumire");
            Integer cantitate=resultSet.getInt("cantitate");

                // Construiește și returnează obiectul Produs cu datele extrase
                return new Produs(id, nume,cantitate);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProdusDAO:findByDenumire " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null; // Dacă nu s-a găsit niciun rând cu denumirea specificată
    }


}
