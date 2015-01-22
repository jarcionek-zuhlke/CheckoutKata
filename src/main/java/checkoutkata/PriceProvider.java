package checkoutkata;

import java.math.BigDecimal;

public interface PriceProvider {

    public BigDecimal getPrice(Item item);

}
