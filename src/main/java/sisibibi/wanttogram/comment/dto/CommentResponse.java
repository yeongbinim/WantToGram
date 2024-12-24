package sisibibi.wanttogram.comment.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {

	private Long id;
	private String content;
	private String author;
	private String createdAt;
	private String updatedAt;
	private List<CommentResponse> comments;
}
