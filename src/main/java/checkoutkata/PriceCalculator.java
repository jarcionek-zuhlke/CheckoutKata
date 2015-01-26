package checkoutkata;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PriceCalculator {

    private final PriceProvider priceProvider;

    public PriceCalculator(PriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    public int calculateTotalPriceFor(Stream<Item> items, Map<Item, Offer> offers) {
        return items
                .reduce(
                        new Result(offers),
                        Result::withCostAndPossibleDiscountOfItem,
                        (currentItem, nextItem) -> nextItem
                )
                .total;
    }

    private class Result {

        private final Map<Item, CountingOffer> countingOffers;

        private int total = 0;

        public Result(Map<Item, Offer> offers) {
            this.countingOffers = new HashMap<>();
            offers.entrySet().forEach(entry -> countingOffers.put(entry.getKey(), new CountingOffer(entry.getValue())));
        }

        private Result withCostAndPossibleDiscountOfItem(Item item) {
            int individualItemPrice = priceProvider.getPrice(item);
            total += individualItemPrice;
            CountingOffer countingOffer = countingOffers.get(item);
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
                return standardPriceOfMultipleItems - offer.getSpecialPrice();
            }
            return 0;
        }

    }

}
