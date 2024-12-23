package sisibibi.wanttogram.member.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByEmail(String email);

    Optional<MemberEntity> findByEmail(String email);
}
