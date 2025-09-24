package io.demo.cft.repo;

import io.demo.cft.domain.Activity;
import io.demo.cft.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
  List<Activity> findByUserOrderByTimestampDesc(User user);
}
