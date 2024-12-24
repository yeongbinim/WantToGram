package sisibibi.wanttogram.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sisibibi.wanttogram.feed.entity.FeedEntity;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {

    default FeedEntity findFeedByIdOrElseThrowById(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found"));
    }

}
