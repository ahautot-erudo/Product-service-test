package fr.troisIl.evaluation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductServiceTest {

    private Database db = null;
    private ProductService productService;

    private int countBefore = 0;

    private Product product;

    @Before
    public void setUp() throws SQLException {
        String testDatabaseFileName = "product.db";

        // reset la BDD avant le test
        File file = new File(testDatabaseFileName);
        file.delete();

        db = new Database(testDatabaseFileName);
        db.createBasicSqlTable();

        productService = new ProductService(db);

        countBefore = count();

        product = new Product();
        product.setLabel("Iphone 15");
        product.setQuantity(10);
    }

    /**
     * Compte les produits en BDD
     *
     * @return le nombre de produit en BDD
     */
    private int count() throws SQLException {
        ResultSet resultSet = db.executeSelect("Select count(*) from Product");
        assertNotNull(resultSet);
        return resultSet.getInt(1);
    }

    /******************** TEST D'INSERTION ***********************/

    @Test
    public void testInsert() throws SQLException {
        productService.insert(product);
    }

    @Test(expected = RuntimeException.class)
    public void testInsert_NullProduct_ThrowsException() {
        productService.insert(null);
    }

    @Test
    public void testInsert_NullProduct() {
        productService.insert(null);
    }

    @Test(expected = RuntimeException.class)
    public void testInsert_NullLabel_ThrowsException() {
        product.setLabel(null);
        productService.insert(product);
    }

    @Test
    public void testInsert_NullLabel() {
        product.setLabel(null);
        productService.insert(product);
    }

    @Test
    public void testInsert_NullQuantity() {
        product.setQuantity(null);
        productService.insert(product);
        assertEquals(0, product.getQuantity().intValue());
    }

    // Insertion avec verification dans la base de donnees
    @Test
    public void testInsert_ValidProduct() {
        Product prod = productService.insert(product);
        assertEquals(product, prod);
    }

    @Test
    public void testUpdate() throws SQLException {
    }

    @Test
    public void testFindById() throws SQLException {
    }

    @Test
    public void testDelete() throws SQLException {
    }

}
