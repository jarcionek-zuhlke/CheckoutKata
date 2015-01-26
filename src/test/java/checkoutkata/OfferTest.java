package checkoutkata;

import org.junit.Test;

public class OfferTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenCreatingOfferWithNonPositiveNumberOfItems() {
        new Offer(0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenCreatingOfferWithNonPositivePrice() {
        new Offer(3, 0);
    }

}