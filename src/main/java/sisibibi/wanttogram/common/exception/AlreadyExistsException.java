package sisibibi.wanttogram.common.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String entity, String message) {
        super(String.format("%s 중복 오류: %s", entity, message));
    }
}