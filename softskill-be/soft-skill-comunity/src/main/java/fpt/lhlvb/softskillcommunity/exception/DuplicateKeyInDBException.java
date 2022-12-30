package fpt.lhlvb.softskillcommunity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DuplicateKeyInDBException extends RuntimeException {

    private static final long serialVersionUID = -5094876347523290424L;

    public DuplicateKeyInDBException(String exception) {
        super(exception);
    }

}
