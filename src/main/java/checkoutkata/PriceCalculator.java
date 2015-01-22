package checkoutkata;

import java.math.BigDecimal;

import static java.util.stream.StreamSupport.stream;

public class PriceCalculator {

    private final PriceProvider priceProvider;

    public PriceCalculator(PriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    public static PriceCalculator priceCalculator() {
        return new PriceCalculator(null);
    }

    public BigDecimal calculateTotalPrice(Iterable<Item> items) {
        if (!items.iterator().hasNext()) {
            throw new IllegalArgumentException("no items provided");
        }

        return stream(items.spliterator(), false)
                .map(priceProvider::getPrice)
                .reduce(BigDecimal::add)
                .get();
    }

}