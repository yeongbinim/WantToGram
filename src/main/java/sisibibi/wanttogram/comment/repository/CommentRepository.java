package sisibibi.wanttogram.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sisibibi.wanttogram.comment.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
