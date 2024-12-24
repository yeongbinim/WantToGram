package sisibibi.wanttogram.comment.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sisibibi.wanttogram.comment.entity.CommentEntity;

@Getter
@AllArgsConstructor
public class CommentResponse {

	private Long id;
	private String content;
	private String author;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<CommentResponse> comments;

	public static CommentResponse from(CommentEntity commentEntity) {
		return new CommentResponse(
			commentEntity.getId(),
			commentEntity.getContent(),
			commentEntity.getMember().getName(),
			commentEntity.getCreatedAt(),
			commentEntity.getUpdatedAt(),
			new ArrayList<>()
		);
	}

	public static List<CommentResponse> from(List<CommentEntity> entities) {
		Map<Long, CommentResponse> commentMap = new HashMap<>();
		List<CommentResponse> rootComments = new ArrayList<>();

		for (CommentEntity comment : entities) {
			CommentResponse response = CommentResponse.from(comment);
			commentMap.put(comment.getId(), response);

			if (comment.getParent() == null) {
				rootComments.add(response);
				continue;
			}
			CommentResponse parent = commentMap.get(comment.getParent().getId());
			if (parent != null) {
				parent.getComments().add(response);
			}
		}

		return rootComments;
	}
}
