package checkoutkata;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

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

        Map<Character, CountingOffer> offerWrappers = new HashMap<>();

        offers.entrySet().forEach(entry -> {offerWrappers.put(entry.getKey(), new CountingOffer(entry.getValue()));});

        return stream(items.spliterator(), false)
                .reduce(
                        new Result(offerWrappers),
                        Result::withCostAndPossibleDiscountOfItem,
                        (currentItem, nextItem) -> nextItem
                )
                .getTotalAsBigDecimal();
    }

    private class Result {

        private final Map<Character, CountingOffer> countingOffers;

        private int total = 0;

        public Result(Map<Character, CountingOffer> countingOffers) {
            this.countingOffers = countingOffers;
        }

        public BigDecimal getTotalAsBigDecimal() {
            return BigDecimal.valueOf(total).divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY);
        }

        private Result withCostAndPossibleDiscountOfItem(Item item) {
            int individualItemPrice = priceOf(item);
            total += individualItemPrice;
            CountingOffer countingOffer = countingOffers.get(item.getSku());
            if (countingOffer != null) {
                total -= countingOffer.calculateDiscount(individualItemPrice);
            }
            return this;
        }

    }

    private class CountingOffer {

        private final Offer offer;

        private int count = 0;

        private CountingOffer(Offer offer) {
            this.offer = offer;
        }

        private int calculateDiscount(int individualItemPrice) {
            count++;
            if (count == offer.getNumberOfItems()) {
                int standardPriceOfMultipleItems = individualItemPrice * count;
                count = 0;
                return standardPriceOfMultipleItems - inPence(offer.getTotalPrice());
            }
            return 0;
        }

    }


    private int priceOf(Item item) {
        return inPence(priceProvider.getPrice(item.getSku()));
    }

    private static int inPence(BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(100)).intValue();
    }

    private static boolean isEmpty(Iterable<Item> items) {
        return !items.iterator().hasNext();
    }

}
