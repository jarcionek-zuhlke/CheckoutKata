package checkoutkata;

import java.util.Map;
import java.util.stream.Stream;

public class PriceFormatter {

    public PriceFormatter(PriceCalculator priceCalculator) {

    }

    public String calculateTotalPriceFor(Stream<Item> items, Map<Item, Offer> offers) {
        return "£0.00";
    }

}
