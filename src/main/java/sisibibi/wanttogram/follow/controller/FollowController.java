package sisibibi.wanttogram.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisibibi.wanttogram.follow.dto.FollowListResponseDto;
import sisibibi.wanttogram.follow.dto.FollowResponseDto;
import sisibibi.wanttogram.follow.service.FollowService;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class FollowController {

    // 속성
    private final FollowService followService;


    // 생성자

    // 기능
    // ::: 팔로우 추가 API
    @PostMapping("/{member_id}/follows")
    public ResponseEntity<FollowResponseDto> saveFollow(
            @PathVariable Long member_id,
            @RequestParam Long following_id
    ) {

        FollowResponseDto savedFollow = followService.save(member_id, following_id);

        return new ResponseEntity<>(savedFollow, HttpStatus.CREATED);
    }

    // ::: 특정 멤버의 팔로우 전체 조회 API
    @GetMapping("/{member_id}/follows")
    public ResponseEntity<List<FollowListResponseDto>> findAllById(@PathVariable Long member_id) {

        List<FollowListResponseDto> followListResponseDtoList = followService.findAllById(member_id);

        return new ResponseEntity<>(followListResponseDtoList, HttpStatus.OK);
    }

    // ::: 팔로우 삭제 API
    @DeleteMapping("/{member_id}/follows/{id}")
    public ResponseEntity<Void> deleteFollow(
            @PathVariable Long member_id,
            @PathVariable Long id
    ) {

        followService.delete(member_id, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
