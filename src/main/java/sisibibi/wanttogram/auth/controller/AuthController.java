package sisibibi.wanttogram.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sisibibi.wanttogram.auth.domain.MemberCreate;
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

	@RequestMapping("/login")
	public ResponseEntity<Void> login() {
		return ResponseEntity.ok().build();
	}
}
