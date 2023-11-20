package be.aidji.zkp.server.signature_validator.infrastructure.exception_handler;

import be.aidji.zkp.server.signature_validator.infrastructure.error_mapper.ErrorResponse;
import be.aidji.zkp.server.signature_validator.infrastructure.error_mapper.ResponseLogger;
import be.aidji.zkp.server.signature_validator.infrastructure.exception.SignatureVerifierException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SignatureVerifierExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> signatureVerifierException (SignatureVerifierException e){
        return ResponseLogger.badRequestHandler(e);
    }
}
