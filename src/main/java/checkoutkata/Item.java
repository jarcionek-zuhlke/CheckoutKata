package checkoutkata;

public class Item {

    private final char sku;

    public Item(char sku) {
        this.sku = sku;
    }

    public char getSku() {
        return sku;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Item that = (Item) object;

        return this.sku == that.sku;
    }

    @Override
    public int hashCode() {
        return (int) sku;
    }

}
