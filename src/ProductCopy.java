public class ProductCopy {
    // добавить модификаторы доступа
    String article;
    String productId;
    String shopId;

    public ProductCopy (String article, String productId, String shopId) {
        this.article = article;
        this.productId = productId;
        this.shopId = shopId;
    }

    public String toString () {
        return "Арт. %s, ID продукта - %s, ID магазина - %s".formatted(article, productId, shopId);
    }
}