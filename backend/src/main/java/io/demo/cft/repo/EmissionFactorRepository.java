package io.demo.cft.repo;

import io.demo.cft.domain.EmissionFactor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmissionFactorRepository extends JpaRepository<EmissionFactor, Long> {
  Optional<EmissionFactor> findByCategoryAndUnit(String category, String unit);
}
