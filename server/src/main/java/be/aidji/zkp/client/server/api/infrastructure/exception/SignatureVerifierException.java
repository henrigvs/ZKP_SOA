package be.aidji.zkp.client.server.api.infrastructure.exception;

public class SignatureVerifierException extends RuntimeException{

    public SignatureVerifierException(String message) {
        super(message);
    }
}
