package sisibibi.wanttogram.feed.repository;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sisibibi.wanttogram.feed.entity.FeedEntity;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {

	default FeedEntity findFeedByIdOrElseThrowById(Long id) {
		return findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found"));
	}

	// :: TODO 서비스 레이어에서 동적쿼리 생성하는거로 변경하는 점 고려하기 (아래와 상당부분 겹침)
	@Query("""
		SELECT f
		FROM FeedEntity f
		WHERE f.updatedAt BETWEEN :start AND :end
		ORDER BY (
				SELECT COUNT(l)
				FROM LikeEntity l
				WHERE l.entityType = 'FEED' AND l.entityId = f.id
			) DESC, f.updatedAt DESC
		""")
	Page<FeedEntity> findAllOrderedByLikes(
		@Param("start") LocalDateTime start,
		@Param("end") LocalDateTime end,
		Pageable pageable);

	// :: TODO 성능테스트 해보면서 쿼리 개선하기 (likeCount 를 차라리.. 테이블이 갖고 있는게 낫지 않을까?)
	@Query("""
		SELECT f
		FROM FeedEntity f
		WHERE (f.writer.id IN (
					SELECT fl.following.id
					FROM FollowEntity fl
					WHERE fl.follower.id = :userId
				) OR f.writer.id = :userId
			) AND f.updatedAt BETWEEN :start AND :end
		ORDER BY (
				SELECT COUNT(l)
				FROM LikeEntity l
				WHERE l.entityType = 'FEED' AND l.entityId = f.id
			) DESC, f.updatedAt DESC
		""")
	Page<FeedEntity> findAllByFollowingOrderedByLikes(@Param("userId") Long userId,
		@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);
}
