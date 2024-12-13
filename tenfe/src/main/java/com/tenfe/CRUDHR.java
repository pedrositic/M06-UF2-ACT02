package com.tenfe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Una opció per mostrar tots els registres a la taula que trieu i que pugui
 * paginar, és a dir, mostri els registres de 10 en 10 o del número que trieu
 * (mireu https://www.mariadbtutorial.com/mariadb-basics/mariadb-limit/).
 * 
 * 
 * Una opció que permeti modificar almenys un dels camps de la taula que trieu.
 * 
 */

public class CRUDHR {

    public boolean CreateDatabase(Connection connection, InputStream input)
            throws IOException, ConnectException, SQLException {
        boolean dupRecord = false;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            StringBuilder sqlStatement = new StringBuilder();
            String line;
            try (Statement statement = connection.createStatement()) {
                while ((line = br.readLine()) != null) {
                    // Ignorar comentaris i línies buides
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("--") || line.startsWith("//") || line.startsWith("/*")) {
                        continue;
                    }
                    // Acumular la línea al buffer
                    sqlStatement.append(line);
                    // el caràcter ";" es considera terminació de sentència SQL
                    if (line.endsWith(";")) {
                        // Eliminar el ";" i executar la instrucción
                        String sql = sqlStatement.toString().replace(";", "").trim();
                        statement.execute(sql);
                        // Reiniciar el buffer para la siguiente instrucción
                        sqlStatement.setLength(0);
                    }
                }
            } catch (SQLException sqle) {
                if (!sqle.getMessage().contains("Duplicate entry")) {
                    System.err.println(sqle.getMessage());
                } else {
                    dupRecord = true;
                    br.readLine();
                }
            }
        }
        return dupRecord;
    }

    // Read sense prepared statements, mostra tots els registres
    public void ReadAllDatabase(Connection connection, String TableName) throws ConnectException, SQLException {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + TableName + ";";
            ResultSet rset = statement.executeQuery(query);
            // obtenim numero de columnes i nom
            int colNum = getColumnNames(rset);
            // Si el nombre de columnes és > 0 procedim a llegir i mostrar els registres
            if (colNum > 0) {
                recorrerRegistres(rset, colNum);
            }
        }
    }

    // Aquest mètode auxiliar podria ser utileria del READ, mostra el nom de les
    // columnes i quantes n'hi ha
    public static int getColumnNames(ResultSet rs) throws SQLException {
        int numberOfColumns = 0;
        if (rs != null) {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            numberOfColumns = rsMetaData.getColumnCount();

            for (int i = 1; i < numberOfColumns + 1; i++) {
                String columnName = rsMetaData.getColumnName(i);
                System.out.print(columnName + ", ");
            }
        }
        System.out.println();
        return numberOfColumns;
    }

    public static int getColumns(ResultSet rs) throws SQLException {
        int numberOfColumns = 0;
        if (rs != null) {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            numberOfColumns = rsMetaData.getColumnCount();
        }
        System.out.println();
        return numberOfColumns;
    }

    public void recorrerRegistres(ResultSet rs, int ColNum) throws SQLException {
        while (rs.next()) {
            for (int i = 0; i < ColNum; i++) {
                if (i + 1 == ColNum) {
                    System.out.println(rs.getString(i + 1));
                } else {
                    System.out.print(rs.getString(i + 1) + ", ");
                }
            }
        }
    }

    private void executeQuery(PreparedStatement prepstat) throws SQLException {
        ResultSet rset = prepstat.executeQuery();
        int colNum = getColumnNames(rset);
        // Si el nombre de columnes és > 0 procedim a llegir i mostrar els registres
        if (colNum > 0) {
            recorrerRegistres(rset, colNum);
        }
    }

    public void ReadEstacioId(Connection connection, String TableName, int id)
            throws ConnectException, SQLException {
        String query = "SELECT * FROM " + TableName + " WHERE id = ?";
        try (PreparedStatement prepstat = connection.prepareStatement(query)) {
            prepstat.setInt(1, id);
            executeQuery(prepstat);
        }
    }

    public void ReadEstacioNom(Connection connection, String TableName, String nom)
            throws ConnectException, SQLException {
        String query = "SELECT * FROM estacio WHERE nom LIKE ?";
        try (PreparedStatement prepstat = connection.prepareStatement(query)) {
            prepstat.setString(1, "%" + nom + "%");
            executeQuery(prepstat);
        }
    }

    public void InsertEstacio(Connection connection, String nom) throws ConnectException, SQLException, IOException {
        connection.setAutoCommit(false);

        // Primer mirem si hi ha alguna estacio amb el nom que asignarem
        if (checkIfRecordsExist(connection, "estacio", "nom", nom)) {
            System.out.println("Ja existeix una estació amb aquest nom");
        } else {
            String query = "INSERT INTO estacio (id, nom) VALUES(?,?)";
            try (PreparedStatement prepstat = connection.prepareStatement(query)) {
                int id = getMaxId(connection, "estacio") + 1;
                prepstat.setInt(1, id);
                prepstat.setString(2, nom);
                prepstat.executeUpdate();
                System.out.println("Estacio inserida!");
                connection.commit();

                System.out.println("ID: " + id + " NOM: " + nom);

            }
            connection.setAutoCommit(true);
        }
    }

    public void DeleteEstacio(Connection connection, int id) throws ConnectException, SQLException {
        connection.setAutoCommit(false);

        // Comprovem que la estacio existeix
        if (checkIfRecordsExist(connection, "estacio", "id", id)) {
            String query = "DELETE FROM estacio WHERE id = ?";
            try (PreparedStatement prepstat = connection.prepareStatement(query)) {
                prepstat.setInt(1, id);
                prepstat.executeUpdate();
                System.out.println("Estacio Eliminada!");
                connection.commit();
            }
            connection.setAutoCommit(true);
        }
    }

    private boolean hasRecords(PreparedStatement prepstat) throws SQLException {
        try (ResultSet rset = prepstat.executeQuery()) {
            return rset.next();
        }
    }

    public boolean checkIfRecordsExist(Connection connection, String tableName, String columnName, Object value)
            throws SQLException {
        // Construir una consulta generica per verificar si hi ha registres
        String query = "SELECT 1 FROM " + tableName + " WHERE " + columnName + " = ? LIMIT 1";

        try (PreparedStatement prepstat = connection.prepareStatement(query)) {

            if (value instanceof Integer) {
                prepstat.setInt(1, (Integer) value);
            } else if (value instanceof String) {
                prepstat.setString(1, (String) value);
            } else {
                throw new IllegalArgumentException("Unsupported parameter type");
            }

            return hasRecords(prepstat);
        }
    }

    private int getMaxId(Connection connection, String tableName) throws SQLException {
        int id = 0;
        String query = "SELECT MAX(id) FROM " + tableName;

        try (PreparedStatement prepstat = connection.prepareStatement(query)) {
            ResultSet rset = prepstat.executeQuery();
            int colNum = getColumns(rset);
            // Si el nombre de columnes és > 0 procedim a llegir i mostrar els registres
            if (colNum > 0) {
                while (rset.next()) {
                    for (int i = 0; i < colNum; i++) {
                        id = rset.getInt(i + 1);
                    }
                }
            }
        }
        return id;
    }

}
