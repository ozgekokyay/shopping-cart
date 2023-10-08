package trendyolCase.checkout.exceptions;

public class CartTotalExceededException extends Exception {
    public CartTotalExceededException(String message) {
        super(message);
    }
}
