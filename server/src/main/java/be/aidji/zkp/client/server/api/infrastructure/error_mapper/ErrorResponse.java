package be.aidji.zkp.client.server.api.infrastructure.error_mapper;

public class ErrorResponse {

    private final String message;
    private final int statusCode;

    public ErrorResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }


    @Override
    public String toString() {
        return "{" +
                "message: " + (this.getMessage() != null ? this.getMessage() : "null") + ", " +
                "status_code: " + this.getStatusCode() + ", " +
                "}";
    }
}
