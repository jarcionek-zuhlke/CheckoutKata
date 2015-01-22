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
    public BigDecimal getPrice(char sku) {
        if (!prices.containsKey(sku)) {
            throw new IllegalArgumentException(String.format("No price defined for sku \"%s\"", sku));
        }
        return prices.get(sku);
    }

}
