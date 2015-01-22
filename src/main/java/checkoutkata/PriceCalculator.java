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
        BigDecimal total = BigDecimal.ZERO;

        for (Item item : items) {
            total = total.add(priceProvider.getPrice(item));
        }

        return total;
    }

}
