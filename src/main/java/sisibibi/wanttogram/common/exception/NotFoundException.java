package sisibibi.wanttogram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

	public NotFoundException(String resource, Long id) {
		super(HttpStatus.NOT_FOUND, String.format("%s에서 id: %s 를 찾을 수 없습니다.", resource, id));
	}
	public NotFoundException(String resource, String email) {
		super(HttpStatus.NOT_FOUND, String.format("%s에서 email: %s 를 찾을 수 없습니다.", resource, email));
	}
	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
