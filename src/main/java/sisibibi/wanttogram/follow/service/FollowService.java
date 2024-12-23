package sisibibi.wanttogram.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sisibibi.wanttogram.follow.dto.FollowListResponseDto;
import sisibibi.wanttogram.follow.dto.FollowResponseDto;
import sisibibi.wanttogram.follow.dto.FollowerMemberResponseDto;
import sisibibi.wanttogram.follow.dto.FollowingMemberResponseDto;
import sisibibi.wanttogram.follow.entity.FollowEntity;
import sisibibi.wanttogram.follow.infrastructure.FollowRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

import java.util.List;

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

    // 특정 멤버의 팔로우 전체 조회
    public List<FollowListResponseDto> findAllById(Long following_id) {

        List<FollowEntity> followEntityList = followRepository.findByFollowingId(following_id);

        if (followEntityList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 팔로잉 멤버가 없습니다.");
        }

        return followEntityList.stream()
                .map(FollowListResponseDto::followDto)
                .toList();
    }

    // 팔로우 삭제
    @Transactional
    public void delete(Long member_id, Long id) {

        FollowEntity foundFollow = followRepository.findByIdOrElseThrow(id);

        if (foundFollow.getFollower().getId().equals(member_id)) {
            followRepository.delete(foundFollow);
        }
    }
}
