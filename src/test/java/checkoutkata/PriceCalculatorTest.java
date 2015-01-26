package checkoutkata;

import com.google.common.collect.ImmutableMap;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Collections.emptyMap;

public class PriceCalculatorTest {

    private final Mockery context = new Mockery();

    private final PriceProvider priceProvider = context.mock(PriceProvider.class);

    private final PriceCalculator priceCalculator = new PriceCalculator(priceProvider);

    @Test
    public void returnsPriceOfItemForSingleItem() {
        Item item = new Item('A');
        priceOf(item, is(42));

        int total = priceCalculator.calculateTotalPriceFor(stream(item), emptyMap());

        assertThat(total, sameBeanAs(42));
    }

    @Test
    public void returnsTotalPriceOfTwoDifferentItems() {
        Item itemOne = new Item('B');
        Item itemTwo = new Item('C');
        priceOf(itemOne, is(13));
        priceOf(itemTwo, is(16));

        int total = priceCalculator.calculateTotalPriceFor(stream(itemOne, itemTwo), emptyMap());

        assertThat(total, sameBeanAs(29));
    }

    @Test
    public void returnsTotalPriceOfItemsInOfferWithTheNumberOfItemsEqualToItemsNeededToQualifyForOffer() {
        Item item = new Item('C');
        priceOf(item, is(12));

        Stream<Item> items = stream(item, item, item, item, item);
        Map<Item, Offer> offers = ImmutableMap.of(item, new Offer(5, 50));

        int total = priceCalculator.calculateTotalPriceFor(items, offers);

        assertThat(total, sameBeanAs(50));
    }

    @Test
    public void returnsTotalPriceOfItemsInOfferWithNumberOfItemsEqualToMultipleOfItemsNeededToQualifyForOffer() {
        Item item = new Item('G');
        priceOf(item, is(33));

        Stream<Item> items = stream(item, item, item, item, item, item);
        Map<Item, Offer> offers = ImmutableMap.of(item, new Offer(2, 60));

        int total = priceCalculator.calculateTotalPriceFor(items, offers);

        assertThat(total, sameBeanAs(180));
    }

    @Test
    public void returnsTotalPriceOfItemsInOfferWithOnlyFewItemsQualifyingForTheOffer() {
        Item item = new Item('H');
        priceOf(item, is(25));

        Stream<Item> items = stream(item, item, item, item, item);
        Map<Item, Offer> offers = ImmutableMap.of(item, new Offer(4, 75));

        int total = priceCalculator.calculateTotalPriceFor(items, offers);

        assertThat(total, sameBeanAs(100));
    }


    private void priceOf(final Item item, final int price) {
        context.checking(new Expectations() {{
            allowing(priceProvider).getPrice(item); will(returnValue(price));
        }});
    }

    private static int is(int value) {
        return value;
    }

    private static Stream<Item> stream(Item... objects) {
        return Arrays.stream(objects);
    }

}