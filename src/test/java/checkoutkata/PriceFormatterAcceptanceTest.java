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

@SuppressWarnings("UnusedDeclaration")
public class PriceFormatterAcceptanceTest {

    private static final int PRICE_OF_A = 50;
    private static final int PRICE_OF_B = 30;
    private static final int PRICE_OF_D = 15;
    private static final int SPECIAL_PRICE_OF_3_A = 130;
    private static final int SPECIAL_PRICE_OF_2_B = 45;

    private final PriceFormatter priceFormatter = Config.priceFormatter();

    @Test
    public void returnsStringRepresentationOfTheTotalPrice() {
        Stream<Item> items = items('B', 'A', 'B', 'B', 'D');
        Map<Item, Offer> offers = ImmutableMap.of(new Item('A'), new Offer(3, SPECIAL_PRICE_OF_3_A), new Item('B'), new Offer(2, SPECIAL_PRICE_OF_2_B));

        String totalPrice = priceFormatter.calculateTotalPriceFor(items, offers);

        assertThat(totalPrice, is(sameBeanAs("£1.40"))); // 0.30 + 0.45 + 0.50 + 0.15
    }

    private static Stream<Item> items(Character... skus) {
        return stream(skus).map(Item::new);
    }

}