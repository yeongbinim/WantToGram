package sisibibi.wanttogram.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowResponseDto {

    // 속성
    private final Long id;
    private final FollowerMemberResponseDto follower;
    private final FollowingMemberResponseDto following;

    // 생성자

    // 기능
}
