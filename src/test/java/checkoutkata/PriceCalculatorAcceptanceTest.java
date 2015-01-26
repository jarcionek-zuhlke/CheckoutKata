package checkoutkata;

import org.junit.Test;

import java.util.Collections;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.stream;
import static org.hamcrest.CoreMatchers.is;

public class PriceCalculatorAcceptanceTest {

    private static final int PRICE_OF_A = 50;
    private static final int PRICE_OF_B = 30;
    private static final int PRICE_OF_C = 20;
    private static final int PRICE_OF_D = 15;
    private static final int SPECIAL_PRICE_OF_3_A = 130;
    private static final int SPECIAL_PRICE_OF_2_B = 45;

    private final PriceCalculator priceCalculator = Config.priceCalculator();

    @Test
    public void calculatesThePriceOfMultipleItemsWithoutSpecialOffers() {
        Iterable<Item> items = items('A', 'D', 'B', 'D', 'C');
        Iterable<Offer> noSpecialOffers = Collections.emptyList();

        int total = priceCalculator.calculateTotalPriceFor(items, noSpecialOffers);

        assertThat(total, is(sameBeanAs(PRICE_OF_A + PRICE_OF_D + PRICE_OF_B + PRICE_OF_D + PRICE_OF_C)));
    }

    @Test
    public void calculatesThePriceOfMultipleItemsWithSpecialOffers() {
        Iterable<Item> items = items('A', 'A', 'A', 'A', 'B', 'B', 'C', 'C', 'C');
        Iterable<Offer> specialOffers = newArrayList(new Offer('A', 3, SPECIAL_PRICE_OF_3_A), new Offer('B', 2, SPECIAL_PRICE_OF_2_B));

        int total = priceCalculator.calculateTotalPriceFor(items, specialOffers);

        assertThat(total, is(sameBeanAs(SPECIAL_PRICE_OF_3_A + PRICE_OF_A + SPECIAL_PRICE_OF_2_B + 3 * PRICE_OF_C)));
    }


    private static Iterable<Item> items(Character... skus) {
        return stream(skus).map(Item::new).collect(Collectors.toList());
    }

}
