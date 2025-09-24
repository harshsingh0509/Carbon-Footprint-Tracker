package io.demo.cft.api;

import io.demo.cft.domain.Activity;
import io.demo.cft.domain.User;
import io.demo.cft.repo.UserRepository;
import io.demo.cft.service.ActivityService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/activities") @Validated @RequiredArgsConstructor
public class ActivityController {
  private final ActivityService svc;
  private final UserRepository users;

  record AddReq(@NotBlank String email, @NotBlank String category, @Positive double amount, @NotBlank String unit) {}

  @PostMapping
  public ResponseEntity<Activity> add(@RequestBody AddReq req) {
    User u = users.findByEmail(req.email()).orElseThrow();
    return ResponseEntity.ok(svc.add(u, req.category(), req.amount(), req.unit()));
  }

  @GetMapping
  public ResponseEntity<List<Activity>> list(@RequestParam String email) {
    User u = users.findByEmail(email).orElseThrow();
    return ResponseEntity.ok(svc.list(u));
  }

  @GetMapping("/summary")
  public ResponseEntity<Map<String, Object>> summary(@RequestParam String email) {
    User u = users.findByEmail(email).orElseThrow();
    return ResponseEntity.ok(Map.of("totalKgCO2e", svc.total(u)));
  }
}
