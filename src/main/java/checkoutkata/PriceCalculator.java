package checkoutkata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.StreamSupport.stream;

public class PriceCalculator {

    private final PriceProvider priceProvider;

    public PriceCalculator(PriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    public BigDecimal calculateTotalPriceFor(Iterable<Item> items, Iterable<Offer> specialOffers) {
        if (!items.iterator().hasNext()) {
            throw new IllegalArgumentException("no items provided");
        }

        Map<Character, Long> count = stream(items.spliterator(), false).collect(groupingBy(Item::getSku, counting()));

        Map<Character, Offer> offers = new HashMap<>();
        for (Offer offer : specialOffers) {
            offers.put(offer.getItemSku(), offer);
        }

        return count.entrySet().stream()
                .map(e -> {
                    char sku = e.getKey();
                    BigDecimal individualPrice = priceProvider.getPrice(sku);
                    long itemCount = count.get(sku);
                    if (offers.containsKey(sku)) {
                        Offer offer = offers.get(sku);
                        long multiples = itemCount / offer.getNumberOfItems();
                        long remainder = itemCount % offer.getNumberOfItems();
                        return offer.getTotalPrice().multiply(BigDecimal.valueOf(multiples))
                                .add(individualPrice.multiply(BigDecimal.valueOf(remainder)));
                    } else {
                        return individualPrice.multiply(BigDecimal.valueOf(itemCount));
                    }
                })
                .reduce(BigDecimal::add).get();
    }

}
