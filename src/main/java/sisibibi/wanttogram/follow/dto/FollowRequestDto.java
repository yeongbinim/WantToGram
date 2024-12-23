package sisibibi.wanttogram.follow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowRequestDto {

    // 속성
    @NotBlank
    private final Long following_id;

    // 생성자


    // 기능
}
