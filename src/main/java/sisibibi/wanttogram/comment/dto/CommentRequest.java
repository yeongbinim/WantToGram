package sisibibi.wanttogram.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequest {

	@NotBlank(message = "Content cannot be blank.")
	@Size(max = 100, message = "Content must be 100 characters or fewer.")
	private String content;
}
