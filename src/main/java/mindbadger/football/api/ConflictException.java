package mindbadger.football.api;


import io.crnk.core.engine.document.ErrorData;
import io.crnk.core.engine.http.HttpStatus;
import io.crnk.core.exception.CrnkMappableException;

/**
 * Thrown when resource for a type conflicts
 */
public final class ConflictException extends CrnkMappableException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT_409, ErrorData.builder().setTitle(message).setDetail(message)
                .setStatus(String.valueOf(HttpStatus.CONFLICT_409)).build());
    }

}
