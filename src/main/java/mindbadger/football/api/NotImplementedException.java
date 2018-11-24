package mindbadger.football.api;


import io.crnk.core.engine.document.ErrorData;
import io.crnk.core.engine.http.HttpStatus;
import io.crnk.core.exception.CrnkMappableException;

/**
 * Thrown when resource for a type conflicts
 */
public final class NotImplementedException extends CrnkMappableException {

    public NotImplementedException(String message) {
        super(HttpStatus.NOT_IMPLEMENTED_501, ErrorData.builder().setTitle(message).setDetail(message)
                .setStatus(String.valueOf(HttpStatus.NOT_IMPLEMENTED_501)).build());
    }

}
