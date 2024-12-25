package sisibibi.wanttogram.feed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sisibibi.wanttogram.feed.entity.FeedEntity;

import java.time.LocalDateTime;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {

    default FeedEntity findFeedByIdOrElseThrowById(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found"));
    }

    Page<FeedEntity> findAllByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("SELECT f FROM FeedEntity f WHERE f.writer.id IN (SELECT fl.following.id FROM FollowEntity fl WHERE fl.follower.id = :userId) " +
            "OR f.writer.id = :userId")
    Page<FeedEntity> findAllFeedsByFollowing(@Param("userId") Long userId, Pageable pageable);


}
