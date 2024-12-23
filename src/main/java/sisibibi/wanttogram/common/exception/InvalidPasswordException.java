package sisibibi.wanttogram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPasswordException extends ResponseStatusException {

	public InvalidPasswordException() {
		super(HttpStatus.BAD_REQUEST, "제공된 비밀번호가 올바르지 않습니다.");
	}
}
