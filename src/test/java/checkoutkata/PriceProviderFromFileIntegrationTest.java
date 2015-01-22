package checkoutkata;

import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class PriceProviderFromFileIntegrationTest {

    private static final File FILE = resourceFile("/testPrices.txt");

    private final PriceProviderFromFile priceProvider = new PriceProviderFromFile(FILE);

    @Test
    public void returnsPricesFromFile() {
        BigDecimal priceOne = priceProvider.getPrice('A');
        BigDecimal priceTwo = priceProvider.getPrice('B');

        assertThat(priceOne, is(sameBeanAs(new BigDecimal("0.11"))));
        assertThat(priceTwo, is(sameBeanAs(new BigDecimal("2.20"))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenPriceIsUnknown() {
        priceProvider.getPrice('Z');
    }


    private static File resourceFile(String name) {
        try {
            return new File(PriceProviderFromFileIntegrationTest.class.getResource(name).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}