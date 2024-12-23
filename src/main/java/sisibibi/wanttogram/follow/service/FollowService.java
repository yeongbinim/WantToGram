package sisibibi.wanttogram.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisibibi.wanttogram.follow.dto.FollowResponseDto;
import sisibibi.wanttogram.follow.dto.FollowerMemberResponseDto;
import sisibibi.wanttogram.follow.dto.FollowingMemberResponseDto;
import sisibibi.wanttogram.follow.entity.FollowEntity;
import sisibibi.wanttogram.follow.infrastructure.FollowRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    // 속성
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    // 생성자

    // 기능
    // 팔로우 추가
    public FollowResponseDto save(Long member_id, Long following_id) {

        MemberEntity follower = memberRepository.findByIdOrElseThrow(member_id);
        MemberEntity following = memberRepository.findByIdOrElseThrow(following_id);

        FollowEntity follow = new FollowEntity();
        follow.setFollower(follower);
        follow.setFollowing(following);

        FollowEntity savedFollow = followRepository.save(follow);

        FollowerMemberResponseDto followerMemberResponseDto = new FollowerMemberResponseDto(savedFollow.getFollower().getId(), savedFollow.getFollower().getName(), savedFollow.getFollower().getEmail());
        FollowingMemberResponseDto followingMemberResponseDto = new FollowingMemberResponseDto(savedFollow.getFollowing().getId(), savedFollow.getFollowing().getName(), savedFollow.getFollowing().getEmail());

        return new FollowResponseDto(savedFollow.getId(), followerMemberResponseDto, followingMemberResponseDto);
    }

    // 팔로우 삭제
    @Transactional
    public void delete(Long member_id, Long id) {

        FollowEntity foundFollow = followRepository.findByIdOrElseThrow(id);

        if (foundFollow.getFollower().getId() == member_id) {
            followRepository.delete(foundFollow);
        }
    }
}
