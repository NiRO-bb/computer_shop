public class Transaction {
    // добавить модификатор доступа
    int id;
    String type;
    String shopId;
    String productId;
    int amount;
    String responsible;

    public Transaction(int id, String type, String shopId, String productId, int amount, String responsible) {
        this.id = id;
        this.type = type;
        this.shopId = shopId;
        this.productId = productId;
        this.amount = amount;
        this.responsible = responsible;
    }

    public String toString() {
        return "ID - %s, %s, магазин - %s, товар - %s, кол-во - %s, ответственный - %s".formatted(id, type, shopId, productId, amount, responsible);
    }
}