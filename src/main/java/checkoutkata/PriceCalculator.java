package checkoutkata;

import java.util.Map;
import java.util.stream.Stream;

public interface PriceCalculator {

    int calculateTotalPriceFor(Stream<Item> items, Map<Item, Offer> offers);

}
