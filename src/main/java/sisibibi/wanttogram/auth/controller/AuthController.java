package sisibibi.wanttogram.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@RequestMapping("/login")
	public ResponseEntity<Void> login() {
		return ResponseEntity.ok().build();
	}
}
