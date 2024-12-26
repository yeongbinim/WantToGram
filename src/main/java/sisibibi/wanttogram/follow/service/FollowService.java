package sisibibi.wanttogram.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisibibi.wanttogram.common.exception.AlreadyExistsException;
import sisibibi.wanttogram.common.exception.AlreadyFollowException;
import sisibibi.wanttogram.follow.dto.*;
import sisibibi.wanttogram.follow.entity.FollowEntity;
import sisibibi.wanttogram.follow.infrastructure.FollowRepository;
import sisibibi.wanttogram.like.entity.LikeEntity;
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
    public FollowResponseDto save(Long member_id, FollowRequestDto requestDto) {

        MemberEntity follower = memberRepository.findByIdOrElseThrow(member_id);
        MemberEntity following = memberRepository.findByIdOrElseThrow(requestDto.getFollowing_id());

        alreadyFollowedCheck(member_id, requestDto.getFollowing_id());

        FollowEntity follow = new FollowEntity();
        follow.setFollower(follower);
        follow.setFollowing(following);

        FollowEntity savedFollow = followRepository.save(follow);

        FollowerMemberResponseDto followerMemberResponseDto = new FollowerMemberResponseDto(savedFollow.getFollower().getId(), savedFollow.getFollower().getName(), savedFollow.getFollower().getEmail());
        FollowingMemberResponseDto followingMemberResponseDto = new FollowingMemberResponseDto(savedFollow.getFollowing().getId(), savedFollow.getFollowing().getName(), savedFollow.getFollowing().getEmail());

        return new FollowResponseDto(savedFollow.getId(), followerMemberResponseDto, followingMemberResponseDto);
    }

    // 특정 멤버의 팔로잉 전체 조회
    public List<FollowingListResponseDto> findAllByFollowingId(Long following_id) {

        List<FollowEntity> followEntityList = followRepository.findByFollowingId(following_id);

        return followEntityList.stream()
                .map(FollowingListResponseDto::followDto)
                .toList();
    }

    // 팔로워 전체 조회
    public List<FollowerListResponseDto> findAllByFollowerId(Long follower_id) {
        List<FollowEntity> followEntityList = followRepository.findByFollowerId(follower_id);

        return followEntityList.stream()
                .map(FollowerListResponseDto::followDto)
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

    public void alreadyFollowedCheck(Long follower_id, Long following_id) {
        boolean alreadyFollowedCheck = followRepository.existsByFollowerIdAndFollowingId(follower_id, following_id);
        if (alreadyFollowedCheck) {
            throw new AlreadyFollowException();
        }
    }
}
