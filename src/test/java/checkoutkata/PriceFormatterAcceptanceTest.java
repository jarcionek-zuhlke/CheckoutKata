package checkoutkata;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.stream;
import static org.hamcrest.CoreMatchers.is;

@SuppressWarnings("UnusedDeclaration")
public class PriceFormatterAcceptanceTest {

    private static final int APPLE_PRICE = 60;
    private static final int ORANGE_PRICE = 25;

    private final PriceFormatter priceFormatter = Config.priceFormatter();

    @Test
    public void returnsStringRepresentationOfTheTotalPrice() {
        Stream<Item> items = items("Orange", "Apple", "Apple");
        Map<Item, Offer> noOffers = Collections.emptyMap();

        String totalPrice = priceFormatter.calculateTotalPriceFor(items, noOffers);

        assertThat(totalPrice, is(sameBeanAs("£1.45")));
    }

    private static Stream<Item> items(String... names) {
        return stream(names).map(Item::new);
    }

}