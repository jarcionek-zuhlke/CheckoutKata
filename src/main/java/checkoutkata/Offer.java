package checkoutkata;

public class Offer {

    private final int numberOfItems;
    private final int specialPrice;

    public Offer(int numberOfItems, int specialPrice) {
        if (numberOfItems <= 0 || specialPrice <= 0) {
            throw new IllegalArgumentException();
        }
        this.numberOfItems = numberOfItems;
        this.specialPrice = specialPrice;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getSpecialPrice() {
        return specialPrice;
    }

}
