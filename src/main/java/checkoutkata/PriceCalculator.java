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
            BigDecimal itemCount = BigDecimal.valueOf(count.get(sku));
            if (offers.containsKey(sku)) {
                total = total.add(offers.get(sku).getTotalPrice());
            } else {
                total = total.add(individualPrice.multiply(itemCount));
            }
        }

        return total;
    }

}
