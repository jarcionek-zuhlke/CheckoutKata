package checkoutkata;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;

public class PriceCalculatorTest {

    private final Mockery context = new Mockery();

    private final PriceProvider priceProvider = context.mock(PriceProvider.class);

    private final PriceCalculator priceCalculator = new PriceCalculator(priceProvider);

    @Test
    public void returnsPriceOfItemForSingleItem() {
        Item item = new Item('A');
        priceOf(item, is("0.42"));

        BigDecimal total = priceCalculator.calculateTotalPrice(newArrayList(item));

        assertThat(total, sameBeanAs(new BigDecimal("0.42")));
    }



    private void priceOf(final Item item, final BigDecimal price) {
        context.checking(new Expectations() {{
            allowing(priceProvider).getPrice(item); will(returnValue(price));
        }});
    }

    private static BigDecimal is(String value) {
        return new BigDecimal(value);
    }

}