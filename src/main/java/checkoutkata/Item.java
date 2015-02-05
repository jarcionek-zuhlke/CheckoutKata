package checkoutkata;

public class Item {

    private final String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
