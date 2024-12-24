package sisibibi.wanttogram.feed.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sisibibi.wanttogram.member.entity.MemberEntity;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FeedResponseDto {

    private Long id;
    private String userName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
