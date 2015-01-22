package checkoutkata;

import java.math.BigDecimal;

public class Offer {

    private final char itemSku;
    private final int numberOfItems;
    private final BigDecimal totalPrice;

    public Offer(char itemSku, int numberOfItems, BigDecimal totalPrice) {
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

}
