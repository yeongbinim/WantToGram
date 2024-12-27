package sisibibi.wanttogram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SelfLikeNotAllowedException extends ResponseStatusException {

	public SelfLikeNotAllowedException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
