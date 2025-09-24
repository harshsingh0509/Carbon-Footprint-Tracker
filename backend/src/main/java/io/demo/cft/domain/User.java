package io.demo.cft.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name="users")
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String email;
  private String password;
  private String role; // ROLE_USER, ROLE_ADMIN
}
