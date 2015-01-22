package checkoutkata;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

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
        priceOf(item, is("0.42"));

        BigDecimal total = priceCalculator.calculateTotalPrice(newArrayList(item), emptyList());

        assertThat(total, sameBeanAs(new BigDecimal("0.42")));
    }

    @Test
    public void returnsTotalPriceOfTwoDifferentItems() {
        Item itemOne = new Item('B');
        Item itemTwo = new Item('C');
        priceOf(itemOne, is("0.13"));
        priceOf(itemTwo, is("0.16"));

        BigDecimal total = priceCalculator.calculateTotalPrice(newArrayList(itemOne, itemTwo), emptyList());

        assertThat(total, sameBeanAs(new BigDecimal("0.29")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIllegalArgumentExceptionForEmptyList() {
        priceCalculator.calculateTotalPrice(new ArrayList<>(), emptyList());
    }

    @Test
    public void returnsTotalPriceOfItemsInOfferWithTheNumberOfItemsEqualToItemsNeededToQualifyForOffer() {
        Item item = new Item('C');
        priceOf(item, is("0.12"));

        Iterable<Item> items = newArrayList(item, item, item, item, item);
        Iterable<Offer> offers = newArrayList(new Offer('C', 5, new BigDecimal("0.50")));

        BigDecimal total = priceCalculator.calculateTotalPrice(items, offers);

        assertThat(total, sameBeanAs(new BigDecimal("0.50")));
    }

    @Test
    public void returnsTotalPriceOfItemsInOfferWithNumberOfItemsEqualToMultipleOfItemsNeegedToQualifyForOffer() {
        Item item = new Item('G');
        priceOf(item, is("0.33"));

        Iterable<Item> items = newArrayList(item, item, item, item, item, item);
        Iterable<Offer> offers = newArrayList(new Offer('G', 2, new BigDecimal("0.60")));

        BigDecimal total = priceCalculator.calculateTotalPrice(items, offers);

        assertThat(total, sameBeanAs(new BigDecimal("1.80")));
    }



    private void priceOf(final Item item, final BigDecimal price) {
        context.checking(new Expectations() {{
            allowing(priceProvider).getPrice(item.getSku()); will(returnValue(price));
        }});
    }

    private static BigDecimal is(String value) {
        return new BigDecimal(value);
    }

}