package io.demo.cft.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmissionFactor {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String category;
  private String unit;
  private double factor; // kg CO2e per unit
}
