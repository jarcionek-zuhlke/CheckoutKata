package checkoutkata;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PriceFormatterTest {

    private static final Stream<Item> ITEMS = Stream.empty();
    private static final Map<Item, Offer> OFFERS = emptyMap();

    private final Mockery context = new Mockery();

    private final PriceCalculator priceCalculator = context.mock(PriceCalculator.class);

    private final PriceFormatter priceFormatter = new PriceFormatter(priceCalculator);

    @Test
    public void formatsWithOneLeadingZeroAndTwoTrailingZeros() {
        priceCalculatorWillReturn(0);

        String formattedPrice = priceFormatter.calculateTotalPriceFor(ITEMS, OFFERS);

        assertThat(formattedPrice, equalTo("£0.00"));
    }


    private void priceCalculatorWillReturn(final int price) {
        context.checking(new Expectations() {{
            allowing(priceCalculator).calculateTotalPriceFor(ITEMS, OFFERS); will(returnValue(price));
        }});
    }

}