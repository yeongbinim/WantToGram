package sisibibi.wanttogram.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisibibi.wanttogram.auth.domain.MemberCreate;
import sisibibi.wanttogram.auth.domain.MemberLogin;
import sisibibi.wanttogram.auth.domain.MemberResign;
import sisibibi.wanttogram.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService service;
	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid @RequestBody MemberCreate memberCreate){
		service.register(memberCreate);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<Void> login(@Valid @RequestBody MemberLogin memberLogin) {
		service.login(memberLogin);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/resign")
	public ResponseEntity<Void> resign(@Valid @RequestBody MemberResign memberResign){
		service.resign(memberResign);
		return ResponseEntity.noContent().build();
	}
}
