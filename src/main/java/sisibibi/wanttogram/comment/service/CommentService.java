package sisibibi.wanttogram.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sisibibi.wanttogram.comment.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
}
