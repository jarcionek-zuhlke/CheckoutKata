package checkoutkata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PriceCalculator {

    private final PriceProvider priceProvider;

    public PriceCalculator(PriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    public BigDecimal calculateTotalPrice(Iterable<Item> items, Iterable<Offer> specialOffers) {
        if (!items.iterator().hasNext()) {
            throw new IllegalArgumentException("no items provided");
        }

        Map<Character, Integer> count = new HashMap<>();
        for (Item item : items) {
            char sku = item.getSku();
            if (count.containsKey(sku)) {
                count.put(sku, count.get(sku) + 1);
            } else {
                count.put(sku, 1);
            }
        }

        Map<Character, Offer> offers = new HashMap<>();
        for (Offer offer : specialOffers) {
            offers.put(offer.getItemSku(), offer);
        }

        BigDecimal total = BigDecimal.ZERO;
        for (Entry<Character, Integer> entry : count.entrySet()) {
            char sku = entry.getKey();
            BigDecimal individualPrice = priceProvider.getPrice(sku);
            int itemCount = count.get(sku);
            if (offers.containsKey(sku)) {
                Offer offer = offers.get(sku);
                int multiples = itemCount / offer.getNumberOfItems();
                int remainder = itemCount % offer.getNumberOfItems();
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
