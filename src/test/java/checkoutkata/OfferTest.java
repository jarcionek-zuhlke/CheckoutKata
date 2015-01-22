package checkoutkata;

import org.junit.Test;

import java.math.BigDecimal;

public class OfferTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenCreatingOfferWithNonPositiveNumberOfItems() {
        new Offer('Y', 0, BigDecimal.TEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenCreatingOfferWithNonPositivePrice() {
        new Offer('X', 3, BigDecimal.ZERO);
    }

}