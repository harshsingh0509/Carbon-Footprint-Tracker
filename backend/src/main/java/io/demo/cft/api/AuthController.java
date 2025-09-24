package io.demo.cft.api;

import io.demo.cft.domain.User;
import io.demo.cft.repo.UserRepository;
import io.demo.cft.service.AuthService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController @RequestMapping("/api/auth") @Validated @RequiredArgsConstructor
public class AuthController {
  private final AuthService auth;
  private final UserRepository users;

  record RegisterReq(@Email String email, @NotBlank String password) {}
  record LoginReq(@Email String email, @NotBlank String password) {}

  @PostConstruct
  void seed() { auth.ensureAdmin(); }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterReq req) {
    User u = auth.register(req.email(), req.password());
    return ResponseEntity.ok(Map.of("id", u.getId(), "email", u.getEmail()));
  }

  // NOTE: For demo, we skip JWT issuance; wire in JWT later.
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginReq req) {
    return users.findByEmail(req.email())
        .map(u -> ResponseEntity.ok(Map.of("token", "demo-token", "email", u.getEmail(), "role", u.getRole())))
        .orElse(ResponseEntity.status(401).body(Map.of("error","Invalid creds")));
  }
}
