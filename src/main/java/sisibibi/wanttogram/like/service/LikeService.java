package sisibibi.wanttogram.like.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sisibibi.wanttogram.common.exception.AlreadyExistsException;
import sisibibi.wanttogram.common.exception.NotFoundException;
import sisibibi.wanttogram.like.entity.LikeEntity;
import sisibibi.wanttogram.like.infrastructure.LikeRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final HttpSession session;

    private final String userEmail = (String) session.getAttribute("userEmail");
    private final MemberEntity member =
            memberRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("멤버",
                    userEmail));

    public void feedlike(Long feedId) {
        boolean alreadyLikedCheck = alreadyLikedCheck(member.getId(), LikeEntity.EntityType.FEED, feedId);
        if(alreadyLikedCheck){
            throw new AlreadyExistsException("좋아요", "해당 피드에 이미 좋아요를 눌렀습니다.");
        }
        LikeEntity likeEntity = new LikeEntity(
                member, LikeEntity.EntityType.FEED, feedId
        );
        likeRepository.save(likeEntity);
    }

    public void feedUnlike(long feedId) {
        boolean alreadyLikedCheck = alreadyLikedCheck(member.getId(), LikeEntity.EntityType.FEED, feedId);
        if(alreadyLikedCheck){
            LikeEntity likeEntity = new LikeEntity(
                    member, LikeEntity.EntityType.FEED, feedId
            );
            likeRepository.delete(likeEntity);
            return;
        }
        throw new NotFoundException("해당 게시물에 좋아요를 하지 않았습니다.");
    }

    public boolean alreadyLikedCheck(Long memberId, LikeEntity.EntityType entityType, Long entityId) {
        return likeRepository.existsByMemberIdAndEntityTypeAndEntityId(memberId, entityType, entityId);
    }

}
