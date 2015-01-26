package checkoutkata;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Collections.emptyList;

public class PriceCalculatorTest {

    private final Mockery context = new Mockery();

    private final PriceProvider priceProvider = context.mock(PriceProvider.class);

    private final PriceCalculator priceCalculator = new PriceCalculator(priceProvider);

    @Test
    public void returnsPriceOfItemForSingleItem() {
        Item item = new Item('A');
        priceOf(item, is(42));

        int total = priceCalculator.calculateTotalPriceFor(stream(item), emptyList());

        assertThat(total, sameBeanAs(42));
    }

    @Test
    public void returnsTotalPriceOfTwoDifferentItems() {
        Item itemOne = new Item('B');
        Item itemTwo = new Item('C');
        priceOf(itemOne, is(13));
        priceOf(itemTwo, is(16));

        int total = priceCalculator.calculateTotalPriceFor(stream(itemOne, itemTwo), emptyList());

        assertThat(total, sameBeanAs(29));
    }

    @Test
    public void returnsTotalPriceOfItemsInOfferWithTheNumberOfItemsEqualToItemsNeededToQualifyForOffer() {
        Item item = new Item('C');
        priceOf(item, is(12));

        Stream<Item> items = stream(item, item, item, item, item);
        Iterable<Offer> offers = newArrayList(new Offer('C', 5, 50));

        int total = priceCalculator.calculateTotalPriceFor(items, offers);

        assertThat(total, sameBeanAs(50));
    }

    @Test
    public void returnsTotalPriceOfItemsInOfferWithNumberOfItemsEqualToMultipleOfItemsNeededToQualifyForOffer() {
        Item item = new Item('G');
        priceOf(item, is(33));

        Stream<Item> items = stream(item, item, item, item, item, item);
        Iterable<Offer> offers = newArrayList(new Offer('G', 2, 60));

        int total = priceCalculator.calculateTotalPriceFor(items, offers);

        assertThat(total, sameBeanAs(180));
    }

    @Test
    public void returnsTotalPriceOfItemsInOfferWithOnlyFewItemsQualifyingForTheOffer() {
        Item item = new Item('H');
        priceOf(item, is(25));

        Stream<Item> items = stream(item, item, item, item, item);
        Iterable<Offer> offers = newArrayList(new Offer('H', 4, 75));

        int total = priceCalculator.calculateTotalPriceFor(items, offers);

        assertThat(total, sameBeanAs(100));
    }


    private void priceOf(final Item item, final int price) {
        context.checking(new Expectations() {{
            allowing(priceProvider).getPrice(item.getSku()); will(returnValue(price));
        }});
    }

    private static int is(int value) {
        return value;
    }

    private static Stream<Item> stream(Item... objects) {
        return Arrays.stream(objects);
    }

}