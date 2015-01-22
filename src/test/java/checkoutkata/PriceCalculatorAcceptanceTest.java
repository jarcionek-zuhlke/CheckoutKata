package checkoutkata;

import org.junit.Test;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class PriceCalculatorAcceptanceTest {

    private static final BigDecimal A_PRICE = new BigDecimal("0.50");
    private static final BigDecimal B_PRICE = new BigDecimal("0.30");
    private static final BigDecimal C_PRICE = new BigDecimal("0.20");
    private static final BigDecimal D_PRICE = new BigDecimal("0.15");

    private final PriceCalculator priceCalculator = Config.priceCalculator();

    @Test
    public void calculatesThePriceOfMultipleItemsWithoutSpecialOffers() {
        Iterable<Item> items = newArrayList(new Item('A'), new Item('D'), new Item('B'), new Item('D'), new Item('C'));

        BigDecimal total = priceCalculator.calculateTotalPrice(items);

        assertThat(total, is(sameBeanAs(sumOf(A_PRICE, D_PRICE, B_PRICE, D_PRICE, C_PRICE))));
    }


    private static BigDecimal sumOf(BigDecimal firstValue, BigDecimal... otherValues) {
        BigDecimal sum = firstValue;
        for (BigDecimal value : otherValues) {
            sum = sum.add(value);
        }
        return sum;
    }

}
