package sisibibi.wanttogram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {
	public UnauthorizedException() {
		super(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
	}
	public UnauthorizedException(String messege) {
		super(HttpStatus.FORBIDDEN, messege);
	}
}
