package sisibibi.wanttogram.like.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sisibibi.wanttogram.like.entity.LikeEntity;
import sisibibi.wanttogram.member.entity.MemberEntity;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity,Long> {
    Optional<LikeEntity> findByMemberAndEntityTypeAndEntityId(MemberEntity member, LikeEntity.EntityType entityType, Long entityId);

    boolean existsByMemberAndEntityTypeAndEntityId(MemberEntity member, LikeEntity.EntityType entityType, Long entityId);
}
