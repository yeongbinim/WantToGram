package sisibibi.wanttogram.comment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sisibibi.wanttogram.comment.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	List<CommentEntity> findByFeedIdOrderByCreatedAtAsc(Long feedId);
}
