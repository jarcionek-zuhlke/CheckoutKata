package checkoutkata;

public class Offer {

    private final int numberOfItems;
    private final int totalPrice;

    public Offer(int numberOfItems, int totalPrice) {
        if (numberOfItems <= 0 || totalPrice <= 0) {
            throw new IllegalArgumentException();
        }
        this.numberOfItems = numberOfItems;
        this.totalPrice = totalPrice;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

}
