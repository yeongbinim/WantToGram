package sisibibi.wanttogram.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateCommentRequest {

    private final String content;
    private final String password;

}
