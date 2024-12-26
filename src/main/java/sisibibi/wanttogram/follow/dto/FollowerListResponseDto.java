package sisibibi.wanttogram.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sisibibi.wanttogram.follow.entity.FollowEntity;

@Getter
@RequiredArgsConstructor
public class FollowerListResponseDto {

    // 속성
    private final Long id;
    private final FollowingMemberResponseDto following;

    // 생성자

    // 기능
    public static FollowerListResponseDto followDto(FollowEntity follow) {
        FollowingMemberResponseDto followingMemberResponseDto = new FollowingMemberResponseDto(
                follow.getFollowing().getId(),
                follow.getFollowing().getName(),
                follow.getFollowing().getEmail()
        );

        return new FollowerListResponseDto(follow.getId(), followingMemberResponseDto);
    }
}
