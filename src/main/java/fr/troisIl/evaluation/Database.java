package fr.troisIl.evaluation;

import java.sql.*;

/**
 * Objet de connection à la base de données
 */
public class Database {
    /**
     * Contient la connexion effective à la base de données
     */
    private Connection connection;
    /**
     * Contient le nom du fichier de la BDD sqlite
     */
    private String databasefilename;

    /**
     * Construit l'objet de communication avec la BDD
     * @param databasefilename le fichier sqlite
     */
    public Database(String databasefilename) {
        this.databasefilename = databasefilename;
        this.establishConnections();
    }

    /**
     * Etabli la connexion a la BDD.
     */
    private void establishConnections() {
        // create database connection
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.databasefilename);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Créé les tables nécessaires au projet
     */
    public void createBasicSqlTable() {
        String sql = "create table if not exists Product (id INTEGER PRIMARY KEY AUTOINCREMENT NULL, label text NOT NULL, quantity int NOT NULL);";
        executeUpdate(sql);
    }

    /**
     * Execute une requete de mise à jour (insert, update, delete)
     * @param sql la requete a executer
     * @return le statement de l'execution
     */
    public Statement executeUpdate(String sql) {
        Statement st;
        try {
            st = connection.createStatement();
            st.setQueryTimeout(30);
            st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return st;
    }

    /**
     * Génère une requete préparer avec la requete SQL fournie
     * @param sql la requete SQL pour la requete préparée
     * @return le prepared statement
     */
    public PreparedStatement generatePrepared(String sql) {
        PreparedStatement st;
        try {
            st = connection.prepareStatement(sql);
            st.setQueryTimeout(30);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return st;
    }

    /**
     * Affiche la première table présente en BDD
     * @return le nom de la première table trouvée
     */
    public String showTable() {
        try {
            String s = "SELECT name FROM sqlite_master WHERE type='table';";
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);
            ResultSet rs = st.executeQuery(s);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Execute une requete SQL de type select simple
     * @param select la requete à executer
     * @return les résultats
     */
    public ResultSet executeSelect(String select) {
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);
            return st.executeQuery(select);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
