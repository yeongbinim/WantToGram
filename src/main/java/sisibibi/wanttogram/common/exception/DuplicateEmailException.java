package sisibibi.wanttogram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateEmailException extends ResponseStatusException {

	public DuplicateEmailException() {
		super(HttpStatus.BAD_REQUEST, "이미 존재하는 회원이 Email입니다.");
	}
}
