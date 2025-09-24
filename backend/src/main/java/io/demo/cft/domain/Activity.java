package io.demo.cft.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Activity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String category; // travel, electricity, food, shopping
  private double amount;   // e.g., km, kWh, kg
  private String unit;     // km,kWh,kg
  private double emission; // computed CO2e kg
  private LocalDateTime timestamp;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
}
