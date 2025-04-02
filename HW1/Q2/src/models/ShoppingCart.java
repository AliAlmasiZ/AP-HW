package models;

public class ShoppingCart extends Order {


    public ShoppingCart() {
        super();
    }

    public Product findProductById(long id) {
        for (Product product : this.productsToQuantity.keySet()) {
            if(product.getID() == id)
                return product;
        }
        return null;
    }
}
