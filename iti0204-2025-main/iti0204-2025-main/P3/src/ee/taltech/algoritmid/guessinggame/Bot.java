package ee.taltech.algoritmid.guessinggame;

public class Bot {
    private Product product;

    public Bot(Product product) {
        this.product = product;
    }

    /**
     * @param productPriceGuess - product price you think is correct
     * @return
     *     "higher" if the correct product price is higher than your guess
     *     "lower" if the correct product price is lower than your guess
     *     "You have won!" if your guess is correct
     */
    public String isIt(Double productPriceGuess) {
        if (this.product.getPrice() > productPriceGuess) {
            return "higher";
        } else if (this.product.getPrice() < productPriceGuess) {
            return "lower";
        }
        return "You have won!";
    }
}
