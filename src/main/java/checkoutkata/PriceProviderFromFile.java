package checkoutkata;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PriceProviderFromFile implements PriceProvider {

    private final Map<Character, BigDecimal> prices = new HashMap<>();

    public PriceProviderFromFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                prices.put(line.charAt(0), new BigDecimal(line.split(",")[1]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getPrice(Item item) {
        if (!prices.containsKey(item.getSku())) {
            throw new IllegalArgumentException(String.format("No price defined for sku \"%s\"", item.getSku()));
        }
        return prices.get(item.getSku());
    }

}
