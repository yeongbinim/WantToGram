package sisibibi.wanttogram.feed.repository;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sisibibi.wanttogram.feed.domain.FeedWithLikesDto;
import sisibibi.wanttogram.feed.entity.FeedEntity;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {

	default FeedEntity findFeedByIdOrElseThrowById(Long id) {
		return findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found"));
	}

	// :: TODO 서비스 레이어에서 동적쿼리 생성하는거로 변경하는 점 고려하기 (아래와 상당부분 겹침)
	@Query("""
		SELECT new sisibibi.wanttogram.feed.domain.FeedWithLikesDto(
		    f.id,
		    f.writer.name,
		    f.title,
		    f.content,
		    f.createdAt,
		    f.updatedAt,
		    COUNT(l.id)
		)
		FROM FeedEntity f
		LEFT JOIN LikeEntity l ON l.entityType = 'FEED' AND l.entityId = f.id
		GROUP BY f.id, f.writer.name, f.title, f.content, f.createdAt, f.updatedAt
		ORDER BY COUNT(l.id) DESC, f.updatedAt DESC
		""")
	Page<FeedWithLikesDto> findAllOrderedByLikes(
		@Param("start") LocalDateTime start,
		@Param("end") LocalDateTime end,
		Pageable pageable);

	@Query("""
		SELECT new sisibibi.wanttogram.feed.domain.FeedWithLikesDto(
		    f.id,
		    f.writer.name,
		    f.title,
		    f.content,
		    f.createdAt,
		    f.updatedAt,
		    COUNT(l.id)
		)
		FROM FeedEntity f
		LEFT JOIN LikeEntity l ON l.entityType = 'FEED' AND l.entityId = f.id
		WHERE (f.writer.id IN (
		            SELECT fl.following.id
		            FROM FollowEntity fl
		            WHERE fl.follower.id = :userId
		        ) OR f.writer.id = :userId)
		AND f.updatedAt BETWEEN :start AND :end
		GROUP BY f.id, f.writer.name, f.title, f.content, f.createdAt, f.updatedAt
		ORDER BY COUNT(l.id) DESC, f.updatedAt DESC
		""")
	Page<FeedWithLikesDto> findAllByFollowingOrderedByLikes(
		@Param("userId") Long userId,
		@Param("start") LocalDateTime start,
		@Param("end") LocalDateTime end,
		Pageable pageable);
}
