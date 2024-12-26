package sisibibi.wanttogram.follow.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sisibibi.wanttogram.follow.dto.FollowRequestDto;
import sisibibi.wanttogram.follow.dto.FollowResponseDto;
import sisibibi.wanttogram.follow.dto.FollowerListResponseDto;
import sisibibi.wanttogram.follow.dto.FollowingListResponseDto;
import sisibibi.wanttogram.follow.service.FollowService;

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
		@RequestBody FollowRequestDto requestDto
	) {

		FollowResponseDto savedFollow = followService.save(member_id, requestDto);

		return new ResponseEntity<>(savedFollow, HttpStatus.CREATED);
	}

	// ::: 특정 멤버의 팔로우 전체 조회 API
	@GetMapping("/{member_id}/follows/followers")
	public ResponseEntity<List<FollowingListResponseDto>> findAllByFollowingId(
		@PathVariable Long member_id) {

		List<FollowingListResponseDto> followingListResponseDtoList = followService.findAllByFollowingId(
			member_id);

		return new ResponseEntity<>(followingListResponseDtoList, HttpStatus.OK);
	}

	// ::: 팔로워 전체 조회 API
	@GetMapping("/{member_id}/follows/followings")
	public ResponseEntity<List<FollowerListResponseDto>> findAllByFollowerId(
		@PathVariable Long member_id) {

		List<FollowerListResponseDto> followerListResponseDtoList = followService.findAllByFollowerId(
			member_id);

		return new ResponseEntity<>(followerListResponseDtoList, HttpStatus.OK);
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
