package sisibibi.wanttogram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SamePasswordException extends ResponseStatusException {

    public SamePasswordException() {
        super(HttpStatus.BAD_REQUEST, "현재 비밀번호와 수정할 비밀번호가 동일합니다.");
    }
}
