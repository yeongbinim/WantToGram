package sisibibi.wanttogram.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sisibibi.wanttogram.comment.entity.CommentEntity;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    CommentEntity findByCommentId(Long commentId);
}
