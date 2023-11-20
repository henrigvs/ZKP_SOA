package be.aidji.zkp.server.signature_validator.infrastructure.exception;

public class SignatureVerifierException extends RuntimeException{

    public SignatureVerifierException(String message) {
        super(message);
    }
}
