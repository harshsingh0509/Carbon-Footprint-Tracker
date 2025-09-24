package io.demo.cft.service;

import io.demo.cft.domain.User;
import io.demo.cft.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthService {
  private final UserRepository users;
  private final PasswordEncoder encoder;

  public User register(String email, String password) {
    User u = User.builder().email(email).password(encoder.encode(password)).role("ROLE_USER").build();
    return users.save(u);
  }

  public User ensureAdmin() {
    return users.findByEmail("admin@demo.io")
      .orElseGet(() -> users.save(User.builder()
        .email("admin@demo.io").password(encoder.encode("admin123"))
        .role("ROLE_ADMIN").build()));
  }
}
