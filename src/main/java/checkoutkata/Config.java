package checkoutkata;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class Config {

    public static PriceFormatter priceFormatter() {
        return new PriceFormatter(priceCalculator());
    }

    public static PriceCalculator priceCalculator() {
        return new PriceCalculator(priceProvider());
    }

    private static PriceProvider priceProvider() {
        return new PriceProviderFromFile(pricesFile());
    }

    private static File pricesFile() {
        try {
            URL resource = Config.class.getResource("/prices.txt");
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
