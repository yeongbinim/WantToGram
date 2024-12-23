package sisibibi.wanttogram.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sisibibi.wanttogram.feed.entity.FeedEntity;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
}
