package sisibibi.wanttogram.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sisibibi.wanttogram.follow.entity.FollowEntity;

@Getter
@RequiredArgsConstructor
public class FollowingListResponseDto {

    // 속성
    private final Long id;
    private final FollowerMemberResponseDto follower;

    // 생성자

    // 기능
    public static FollowingListResponseDto followDto(FollowEntity follow) {
        FollowerMemberResponseDto followerMemberResponseDto = new FollowerMemberResponseDto(
                follow.getFollower().getId(),
                follow.getFollower().getName(),
                follow.getFollower().getEmail()
        );

        return new FollowingListResponseDto(follow.getId(), followerMemberResponseDto);
    }
}
