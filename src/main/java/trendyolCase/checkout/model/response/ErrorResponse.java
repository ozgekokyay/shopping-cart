package trendyolCase.checkout.model.response;

public class ErrorResponse extends MessageResponse{

    public ErrorResponse(boolean result, String message) {
        super(result, message);
    }
}