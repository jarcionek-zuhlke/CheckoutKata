package checkoutkata;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.stream;
import static org.hamcrest.CoreMatchers.is;

public class PriceCalculatorAcceptanceTest {

    private static final BigDecimal PRICE_OF_A = price("0.50");
    private static final BigDecimal PRICE_OF_B = price("0.30");
    private static final BigDecimal PRICE_OF_C = price("0.20");
    private static final BigDecimal PRICE_OF_D = price("0.15");
    private static final BigDecimal SPECIAL_PRICE_OF_3_A = price("1.30");
    private static final BigDecimal SPECIAL_PRICE_OF_2_B = price("0.45");

    private final PriceCalculator priceCalculator = Config.priceCalculator();

    @Test
    public void calculatesThePriceOfMultipleItemsWithoutSpecialOffers() {
        Iterable<Item> items = items('A', 'D', 'B', 'D', 'C');
        Iterable<Offer> noSpecialOffers = Collections.emptyList();

        BigDecimal total = priceCalculator.calculateTotalPriceFor(items, noSpecialOffers);

        assertThat(total, is(sameBeanAs(sumOf(PRICE_OF_A, PRICE_OF_D, PRICE_OF_B, PRICE_OF_D, PRICE_OF_C))));
    }

    @Test
    public void calculatesThePriceOfMultipleItemsWithSpecialOffers() {
        Iterable<Item> items = items('A', 'A', 'A', 'A', 'B', 'B', 'C', 'C', 'C');
        Iterable<Offer> specialOffers = newArrayList(new Offer('A', 3, SPECIAL_PRICE_OF_3_A), new Offer('B', 2, SPECIAL_PRICE_OF_2_B));

        BigDecimal total = priceCalculator.calculateTotalPriceFor(items, specialOffers);

        assertThat(total, is(sameBeanAs(sumOf(SPECIAL_PRICE_OF_3_A, PRICE_OF_A, SPECIAL_PRICE_OF_2_B, PRICE_OF_C, PRICE_OF_C, PRICE_OF_C))));
    }


    private static BigDecimal sumOf(BigDecimal firstValue, BigDecimal... otherValues) {
        BigDecimal sum = firstValue;
        for (BigDecimal value : otherValues) {
            sum = sum.add(value);
        }
        return sum;
    }

    private static BigDecimal price(String price) {
        return new BigDecimal(price);
    }

    private static Iterable<Item> items(Character... skus) {
        return stream(skus).map(Item::new).collect(Collectors.toList());
    }

}
