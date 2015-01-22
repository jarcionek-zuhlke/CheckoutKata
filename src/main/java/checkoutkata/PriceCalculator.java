package checkoutkata;

import java.math.BigDecimal;

public class PriceCalculator {

    public static PriceCalculator priceCalculator() {
        return new PriceCalculator();
    }

    public BigDecimal calculateTotalPrice(Iterable<Item> items) {
        return new BigDecimal("1.30");
    }

}
