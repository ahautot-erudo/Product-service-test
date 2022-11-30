package fr.troisIl.evaluation;

/**
 * Contient les règles métiers sur les produits
 */
public class ProductService {

    /**
     * Le DAO permettant de manipuler les produits en BDD
     */
    private ProductDao productDao;

    /**
     * Créé le service
     * @param database l'objet de connexion a la BDD
     */
    public ProductService(Database database) {
        this.productDao = new ProductDao(database);
    }

    /**
     * Insère le produit fourni en BDD.
     * @param product le produit a inserer
     * @return le produit créé
     */
    public Product insert(Product product) {
        if(product == null) {
            throw new RuntimeException("Le produit ne peut pas être null");
        }
        if(product.getLabel() == null) {
            throw new RuntimeException("Le libellé du produit est requis");
        }
        if(product.getQuantity() == null) {
            product.setQuantity(0);
        }

        //Les controles sont OK, création du produit en BDD
        return productDao.insert(product);
    }

    /**
     * Met à jour le produit fourni en BDD
     * @param product le produit a mettre a jour
     * @return le produit mis a jour
     */
    public Product update(Product product) {
        if(product == null) {
            throw new RuntimeException("Le produit ne peut pas être null");
        }
        if(product.getLabel() == null) {
            throw new RuntimeException("Le libellé du produit est requis");
        }
        if(product.getId() == null) {
            throw new RuntimeException("L'identifiant du produit est requis pour une modification");
        }
        if(product.getQuantity() == null) {
            throw new RuntimeException("La quantité ne peut être nulle");
        }

        // vérifie que le produit existe bien en BDD avant de faire la mise à jour pour ne pas en créer un nouveau
        findById(product.getId());

        //Les controles sont OK, MAJ du produit en BDD
        return productDao.update(product);
    }

    /**
     * Recherche un produit par son identifiant
     * @param id l'identifiant du produit recherché
     * @return le produit
     */
    public Product findById(Integer id) {
        if(id == null) {
            throw new RuntimeException("L'id du produit recherché est requis");
        }
        Product product = productDao.findById(id);
        if(product == null) {
            throw new RuntimeException("Le produit n'a pas été trouvé en BDD");
        }
        return product;
    }

    /**
     * Supprimer le produit du l'id est fourni
     * @param id le produit a supprimer
     */
    public void delete(Integer id) {
        if(id == null) {
            throw new RuntimeException("L'id du produit à supprimer est requis");
        }
        Product product = productDao.findById(id);
        if(product == null) {
            throw new RuntimeException("Le produit n'a pas été trouvé en BDD");
        }
        productDao.delete(id);
    }

}
