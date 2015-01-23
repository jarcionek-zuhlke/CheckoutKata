package checkoutkata;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.StreamSupport.stream;

public class PriceCalculator {

    private final PriceProvider priceProvider;

    public PriceCalculator(PriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    public BigDecimal calculateTotalPriceFor(Iterable<Item> items, Iterable<Offer> specialOffers) {
        if (isEmpty(items)) {
            throw new IllegalArgumentException("no items provided");
        }

        Map<Character, Offer> offers = stream(specialOffers.spliterator(), false)
                .collect(groupingBy(Offer::getItemSku, reducing(null, (a, b) -> b)));

        return stream(items.spliterator(), false)
                .collect(groupingBy(Item::getSku, counting()))
                .entrySet().stream()
                .map(entry -> {
                    long numberOfItems = entry.getValue();
                    BigDecimal individualPrice = priceProvider.getPrice(entry.getKey());
                    Offer offer = offers.get(entry.getKey());
                    if (offer != null) {
                        long multiples = numberOfItems / offer.getNumberOfItems();
                        long remainder = numberOfItems % offer.getNumberOfItems();
                        return offer.getTotalPrice().multiply(BigDecimal.valueOf(multiples))
                                .add(individualPrice.multiply(BigDecimal.valueOf(remainder)));
                    } else {
                        return individualPrice.multiply(BigDecimal.valueOf(numberOfItems));
                    }
                })
                .reduce(BigDecimal::add)
                .get();
    }

    private static boolean isEmpty(Iterable<Item> items) {
        return !items.iterator().hasNext();
    }

}
