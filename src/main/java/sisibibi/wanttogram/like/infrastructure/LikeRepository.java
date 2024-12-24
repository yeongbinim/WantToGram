package sisibibi.wanttogram.like.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sisibibi.wanttogram.like.entity.LikeEntity;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity,Long> {
    boolean existsByMemberIdAndEntityTypeAndEntityId(Long memberId, LikeEntity.EntityType entityType, Long entityId);
}
