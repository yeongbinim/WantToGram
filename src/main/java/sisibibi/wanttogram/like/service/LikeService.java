package sisibibi.wanttogram.like.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sisibibi.wanttogram.comment.entity.CommentEntity;
import sisibibi.wanttogram.comment.repository.CommentRepository;
import sisibibi.wanttogram.common.exception.AlreadyExistsException;
import sisibibi.wanttogram.common.exception.NotFoundException;
import sisibibi.wanttogram.common.exception.SelfLikeNotAllowedException;
import sisibibi.wanttogram.feed.entity.FeedEntity;
import sisibibi.wanttogram.feed.repository.FeedRepository;
import sisibibi.wanttogram.like.entity.LikeEntity;
import sisibibi.wanttogram.like.infrastructure.LikeRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final MemberRepository memberRepository;
	private final CommentRepository commentRepository;
	private final FeedRepository feedRepository;

	private final HttpSession session;

	private MemberEntity getLoggedInMember() {
		String userEmail = (String) session.getAttribute("userEmail");
		return memberRepository.findByEmail(userEmail)
			.orElseThrow(() -> new NotFoundException("멤버", userEmail));
	}


	public void feedlike(Long feedId) {
		MemberEntity member = getLoggedInMember();
		FeedEntity feed = feedRepository.findById(feedId)
			.orElseThrow(() -> new NotFoundException("피드", feedId));
		if (feed.getWriter().getEmail().equals(member.getEmail())) {
			throw new SelfLikeNotAllowedException("본인의 피드에는 좋아요할수 없습니다.");
		}
		alreadyLikedCheck(member, LikeEntity.EntityType.FEED, feedId);
		LikeEntity likeEntity = new LikeEntity(
			member, LikeEntity.EntityType.FEED, feedId
		);
		likeRepository.save(likeEntity);
	}

	public void feedUnlike(long feedId) {
		MemberEntity member = getLoggedInMember();
		LikeEntity likeEntity = likeRepository.findByMemberAndEntityTypeAndEntityId(
				member, LikeEntity.EntityType.FEED, feedId)
			.orElseThrow(() -> new SelfLikeNotAllowedException("해당 댓글에 좋아요를 하지 않았습니다."));

		likeRepository.delete(likeEntity);
	}

	public void commentLike(Long commentsId) {
		MemberEntity member = getLoggedInMember();
		CommentEntity comment = commentRepository.findById(commentsId)
			.orElseThrow(() -> new NotFoundException("댓글", commentsId));
		if (comment.getMember().getEmail().equals(member.getEmail())) {
			throw new SelfLikeNotAllowedException("본인의 댓글에는 좋아요할수 없습니다.");
		}
		alreadyLikedCheck(member, LikeEntity.EntityType.COMMENT, commentsId);
		LikeEntity likeEntity = new LikeEntity(
			member, LikeEntity.EntityType.COMMENT, commentsId
		);
		likeRepository.save(likeEntity);
	}

	public void commentUnLike(Long commentsId) {
		MemberEntity member = getLoggedInMember();
		LikeEntity likeEntity = likeRepository.findByMemberAndEntityTypeAndEntityId(
				member, LikeEntity.EntityType.COMMENT, commentsId)
			.orElseThrow(() -> new SelfLikeNotAllowedException("해당 댓글에 좋아요를 하지 않았습니다."));

		likeRepository.delete(likeEntity);
	}

	public void alreadyLikedCheck(MemberEntity member, LikeEntity.EntityType entityType,
		Long entityId) {
		boolean alreadyLikedCheck = likeRepository.existsByMemberAndEntityTypeAndEntityId(member,
			entityType,
			entityId);
		if (alreadyLikedCheck) {
			throw new AlreadyExistsException("좋아요", "해당 댓글에 이미 좋아요를 눌렀습니다.");
		}
	}

}
