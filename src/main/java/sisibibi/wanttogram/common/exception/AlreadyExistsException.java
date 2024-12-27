package sisibibi.wanttogram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistsException extends ResponseStatusException {

	public AlreadyExistsException(String entity, String message) {
		super(HttpStatus.BAD_REQUEST, String.format("%s 중복 오류: %s", entity, message));
	}
}
