package checkoutkata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.StreamSupport.stream;

public class PriceCalculator {

    private final PriceProvider priceProvider;

    public PriceCalculator(PriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    public BigDecimal calculateTotalPrice(Iterable<Item> items, Iterable<Offer> specialOffers) {
        if (!items.iterator().hasNext()) {
            throw new IllegalArgumentException("no items provided");
        }

        Map<Character, Long> count = stream(items.spliterator(), false).collect(groupingBy(Item::getSku, counting()));

        Map<Character, Offer> offers = new HashMap<>();
        for (Offer offer : specialOffers) {
            offers.put(offer.getItemSku(), offer);
        }

        BigDecimal total = BigDecimal.ZERO;
        for (Entry<Character, Long> entry : count.entrySet()) {
            char sku = entry.getKey();
            BigDecimal individualPrice = priceProvider.getPrice(sku);
            long itemCount = count.get(sku);
            if (offers.containsKey(sku)) {
                Offer offer = offers.get(sku);
                long multiples = itemCount / offer.getNumberOfItems();
                long remainder = itemCount % offer.getNumberOfItems();
                total = total
                        .add(offer.getTotalPrice()).multiply(BigDecimal.valueOf(multiples))
                        .add(individualPrice.multiply(BigDecimal.valueOf(remainder)));
            } else {
                total = total.add(individualPrice.multiply(BigDecimal.valueOf(itemCount)));
            }
        }

        return total;
    }

}
