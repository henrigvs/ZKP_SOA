package be.aidji.zkp.client.server.api.infrastructure.error_mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseLogger {

    public static final Logger LOGGER = LoggerFactory.getLogger(ResponseLogger.class);

    public static ResponseEntity<ErrorResponse> badRequestHandler(RuntimeException e){
        LOGGER.error("Exception raised: ", e);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse itemErrorResponse = new ErrorResponse(e.getMessage(), status.value());
        return new ResponseEntity<>(itemErrorResponse, status);
    }
}
