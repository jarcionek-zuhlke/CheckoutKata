package checkoutkata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class PriceProviderFromFile implements PriceProvider {

    private final Map<Item, Integer> prices = new HashMap<>();

    public PriceProviderFromFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            scanner.forEachRemaining(line -> prices.put(itemFrom(line), priceFrom(line)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getPrice(Item item) {
        if (!prices.containsKey(item)) {
            throw new IllegalArgumentException(String.format("No price defined for \"%s\"", item.getName()));
        }
        return prices.get(item);
    }


    private static Item itemFrom(String line) {
        return new Item(line.split(",")[0]);
    }

    private static int priceFrom(String line) {
        return parseInt(line.split(",")[1]);
    }

}
