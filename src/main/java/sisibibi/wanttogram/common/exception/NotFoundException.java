package sisibibi.wanttogram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

	public NotFoundException(String resource, Long id) {
		super(HttpStatus.NOT_FOUND, String.format("%s에서 id: %s 를 찾을 수 없습니다.", resource, id));
	}
}
