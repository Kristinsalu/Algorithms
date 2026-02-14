package ee.taltech.algoritmid.guessinggame;

import java.util.Arrays;

public class ExampleTest {

    public static final void testExampleProductArray() throws Exception {
        Product[] exampleProductArray = {
                new Product("Harmony Productbook", 22.00),
                new Product("Summit Backpack", 44.00),
                new Product("Eclipse Water Bottle", 32.96),
                new Product("Pinnacle Headphones", 62.22),
                new Product("Breeze Pen", 9.80),
                new Product("Elite Smartwatch", 248.90),
                new Product("Compact Calculator", 13.08),
                new Product("Fusion Mug", 24.69)
        };

        GuessingGame game = new GuessingGame();

        // game one

        final Product productGameOne = exampleProductArray[1];
        Bot botGameOne = new Bot(productGameOne);
        Product[] productsGameOne = Arrays.copyOf(exampleProductArray, exampleProductArray.length);
        final boolean guessResultGameOne = game.gameOne(botGameOne, productsGameOne).equals(productGameOne.getName());
        if (!guessResultGameOne) {
            System.err.println("Game One: Your solution did not guess correctly.");
        } else {
            System.out.println("Game One: Your solution guessed the product correctly. However, make sure it is fast enough and still works correctly when played multiple times.");
        }

        // game two

        final Product productGameTwo = exampleProductArray[3];
        Bot botGameTwo = new Bot(productGameTwo);
        Product[] productsGameTwo = Arrays.copyOf(exampleProductArray, exampleProductArray.length);
        final boolean guessResultGameTwo = game.gameTwo(botGameTwo, productsGameTwo).equals(productGameTwo.getName());
        if (!guessResultGameTwo) {
            System.err.println("Game Two: Your solution did not guess correctly.");
        } else {
            System.out.println("Game Two: Your solution guessed the product correctly. However, make sure it is fast enough and still works correctly when played multiple times.");
        }
    }

    public static void main(String[] args) {
        try {
            testExampleProductArray();
        } catch (Exception ignored) {
            System.err.println("Something went wrong...");
        }
    }
}