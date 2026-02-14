package ee.taltech.algoritmid.guessinggame;

import java.util.Arrays;
import java.util.Comparator;

public class GuessingGame {

    public GuessingGame() {
        // don't remove
    }

    private Product[] sortedProducts;
    /**
     * @param bot - bot which you can ask for the feedback about the price you think should be correct
     * @param productArray - Array from which the bot selected the winning product (NB! is always the SAME ARRAY).
     * @return the name of the correct product.
     */

    public String gameOne(Bot bot, Product[] productArray) {
        // TODO
        if (sortedProducts == null) {
            sortedProducts = Arrays.copyOf(productArray, productArray.length);
            Arrays.sort(sortedProducts, Comparator.comparingDouble(Product::getPrice));
        }
        int n = sortedProducts.length;
        int low = 0;
        int high = n - 1;

        while(low <= high) {
            int mid = (low + high) / 2;
            double testPrice = sortedProducts[mid].getPrice();
            String response = bot.isIt(testPrice);

            if (response.equals("You have won!")) {
                return sortedProducts[mid].getName();
            } else if (response.equals("lower")) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return "no match";
    }

    /**
     * @param bot - bot which you can ask for the feedback about the price you think should be correct
     * @param productArray - Array from which the bot selected the winning product (NB! can be a DIFFERENT ARRAY every time)
     * @return the name of the correct product
     */

    public String gameTwo(Bot bot, Product[] productArray) {
        for (Product p : productArray) {
           String response = bot.isIt(p.getPrice());
           if (response.equals("You have won!")) {
               return p.getName();
           }
        }
        return "no match";
    }
}