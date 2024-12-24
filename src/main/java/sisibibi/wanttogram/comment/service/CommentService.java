package sisibibi.wanttogram.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisibibi.wanttogram.comment.dto.CommentResponse;
import sisibibi.wanttogram.comment.dto.OneCommentResponse;
import sisibibi.wanttogram.comment.dto.UpdateCommentRequest;
import sisibibi.wanttogram.comment.entity.CommentEntity;
import sisibibi.wanttogram.comment.repository.CommentRepository;
import sisibibi.wanttogram.common.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

	// 속성
	private final CommentRepository commentRepository;
	private final PasswordEncoder passwordEncoder;

	// 생성자

	// 기능
	// 댓글 수정
	@Transactional
	public CommentEntity updateComment(Long id, String password, UpdateCommentRequest request) {

		CommentEntity foundComment = commentRepository.findByCommentId(id);

		if (passwordEncoder.matches(password, foundComment.getMember().getPassword())) {
			foundComment.updateComment(request.getContent());
		}

		return foundComment;
	}

	// 댓글 삭제
	@Transactional
	public void deleteComment(Long id, String password) {

		CommentEntity foundComment = commentRepository.findByCommentId(id);

		if (passwordEncoder.matches(password, foundComment.getMember().getPassword())) {
			commentRepository.delete(foundComment);
		}
	}

}
