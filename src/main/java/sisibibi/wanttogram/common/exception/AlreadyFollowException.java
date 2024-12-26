package sisibibi.wanttogram.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyFollowException extends ResponseStatusException {

    public AlreadyFollowException() {
        super(HttpStatus.BAD_REQUEST, "해당 멤버를 이미 팔로우하셨습니다.");
    }
}
