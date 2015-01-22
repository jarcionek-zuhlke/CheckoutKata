package checkoutkata;

import java.math.BigDecimal;

public class PriceCalculator {

    private final PriceProvider priceProvider;

    public PriceCalculator(PriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    public static PriceCalculator priceCalculator() {
        return new PriceCalculator(null);
    }

    public BigDecimal calculateTotalPrice(Iterable<Item> items) {
        return priceProvider.getPrice(items.iterator().next());
    }

}
