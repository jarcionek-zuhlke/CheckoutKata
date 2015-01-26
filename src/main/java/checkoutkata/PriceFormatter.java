package checkoutkata;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.stream.Stream;

public class PriceFormatter {

    private static final NumberFormat FORMAT = new DecimalFormat("£#,##0.00");

    private final PriceCalculator priceCalculator;

    public PriceFormatter(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    public String calculateTotalPriceFor(Stream<Item> items, Map<Item, Offer> offers) {
        int price = priceCalculator.calculateTotalPriceFor(items, offers);
        return FORMAT.format(price / 100.0d);
    }

}
