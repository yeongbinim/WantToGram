package sisibibi.wanttogram.follow.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import sisibibi.wanttogram.follow.entity.FollowEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
}
