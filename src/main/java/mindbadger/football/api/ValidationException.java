package mindbadger.football.api;


import io.crnk.core.engine.document.ErrorData;
import io.crnk.core.engine.http.HttpStatus;
import io.crnk.core.exception.CrnkMappableException;

/**
 * Thrown when resource for a type conflicts
 */
public final class ValidationException extends CrnkMappableException {

    public ValidationException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY_422, ErrorData.builder().setTitle(message).setDetail(message)
                .setStatus(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY_422)).build());
    }

}
