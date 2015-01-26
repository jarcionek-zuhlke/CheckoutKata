package checkoutkata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class PriceProviderFromFile implements PriceProvider {

    private final Map<Character, Integer> prices = new HashMap<>();

    public PriceProviderFromFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            scanner.forEachRemaining(line -> prices.put(skuFrom(line), priceFrom(line)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getPrice(char sku) {
        if (!prices.containsKey(sku)) {
            throw new IllegalArgumentException(String.format("No price defined for sku \"%s\"", sku));
        }
        return prices.get(sku);
    }


    private static char skuFrom(String line) {
        return line.charAt(0);
    }

    private static int priceFrom(String line) {
        return parseInt(line.split(",")[1]);
    }

}
