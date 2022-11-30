package fr.troisIl.evaluation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Objet permettant de gérer les requetes envoyées à la BDD concernant les produits
 */
public class ProductDao {

    /**
     * L'objet de connexion a la bdd
     */
    private Database database;

    public ProductDao(Database database) {
        this.database = database;
    }

    /**
     * Insère le produit fourni en BDD.
     * Ne procède a aucun controle sur le produit fourni
     * @param product le produit a inserer
     * @return le produit créé
     */
    public Product insert(Product product) {
        // génération de la requete SQL d'insertion
        try {
            PreparedStatement ps = database.generatePrepared("INSERT INTO Product(label, quantity) VALUES (?,?)");
            ps.setString(1, product.getLabel());
            ps.setInt(2, product.getQuantity());
            ps.executeUpdate();
            ps.close();

            // récupère l'identifiant du produit créé
            ResultSet rs = database.executeSelect("SELECT max(id) FROM Product");
            product.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    /**
     * Met à jour le produit fourni en BDD
     * Ne procède à aucun controle
     * @param product le produit a mettre a jour
     * @return le produit mis a jour
     */
    public Product update(Product product) {
        // génération de la requete SQL d'insertion
        try {
            PreparedStatement ps = database.generatePrepared("Update Product set label =?, quantity=? WHERE id = ?");
            ps.setString(1, product.getLabel());
            ps.setInt(2, product.getQuantity());
            ps.setInt(3, product.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    /**
     * Recherche un produit par son identifiant
     * @param id l'identifiant du produit recherché
     * @return le produit
     */
    public Product findById(Integer id) {
        Product product = null;
        try {
            PreparedStatement ps = database.generatePrepared("SELECT * FROM Product WHERE id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result != null) {
                product = new Product();
                product.setId(result.getInt("id"));
                product.setLabel(result.getString("label"));
                product.setQuantity(result.getInt("quantity"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    /**
     * Supprimer le produit du l'id est fourni
     * @param id le produit a supprimer
     */
    public void delete(Integer id) {
        try {
            PreparedStatement ps = database.generatePrepared("DELETE FROM Product WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
