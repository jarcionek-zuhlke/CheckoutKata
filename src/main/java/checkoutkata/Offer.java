package checkoutkata;

public class Offer {

    private final char itemSku;
    private final int numberOfItems;
    private final int totalPrice;

    public Offer(char itemSku, int numberOfItems, int totalPrice) {
        if (numberOfItems <= 0 || totalPrice <= 0) {
            throw new IllegalArgumentException();
        }
        this.itemSku = itemSku;
        this.numberOfItems = numberOfItems;
        this.totalPrice = totalPrice;
    }

    public char getItemSku() {
        return itemSku;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

}
