package checkoutkata;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.stream;
import static org.hamcrest.CoreMatchers.is;

public class PriceCalculatorAcceptanceTest {

    private static final int APPLE_PRICE = 60;
    private static final int ORANGE_PRICE = 25;

    private final PriceCalculator priceCalculator = Config.priceCalculator();

    @Test
    public void calculatesThePriceOfMultipleItemsWithoutSpecialOffers() {
        Stream<Item> items = items("Apple", "Apple", "Orange", "Apple");
        Map<Item, Offer> noSpecialOffers = Collections.emptyMap();

        int total = priceCalculator.calculateTotalPriceFor(items, noSpecialOffers);

        assertThat(total, is(sameBeanAs(APPLE_PRICE * 3 + ORANGE_PRICE)));
    }

    @Test
    public void calculatesThePriceOfMultipleItemsWithSpecialOffers() {
        Stream<Item> items = items("Apple", "Apple", "Apple", "Orange", "Orange", "Orange", "Orange");
        Map<Item, Offer> specialOffers = ImmutableMap.of(
                new Item("Apple"), new Offer(2, APPLE_PRICE),
                new Item("Orange"), new Offer(3, ORANGE_PRICE * 2)
        );

        int total = priceCalculator.calculateTotalPriceFor(items, specialOffers);

        assertThat(total, is(sameBeanAs(APPLE_PRICE * 2 + ORANGE_PRICE * 3)));
    }


    private static Stream<Item> items(String... names) {
        return stream(names).map(Item::new);
    }

}
