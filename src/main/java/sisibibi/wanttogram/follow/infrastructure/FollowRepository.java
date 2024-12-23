package sisibibi.wanttogram.follow.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sisibibi.wanttogram.follow.entity.FollowEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    default FollowEntity findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 팔로우가 존재하지 않습니다."));
    }
}
