package io.demo.cft.service;

import io.demo.cft.domain.Activity;
import io.demo.cft.domain.EmissionFactor;
import io.demo.cft.domain.User;
import io.demo.cft.repo.ActivityRepository;
import io.demo.cft.repo.EmissionFactorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class ActivityService {
  private final ActivityRepository activities;
  private final EmissionFactorRepository factors;

  public Activity add(User user, String category, double amount, String unit) {
    double factor = factors.findByCategoryAndUnit(category, unit)
        .map(EmissionFactor::getFactor).orElse(0.2); // default
    Activity a = Activity.builder()
        .user(user).category(category).amount(amount).unit(unit)
        .emission(amount * factor).timestamp(LocalDateTime.now()).build();
    return activities.save(a);
  }

  public List<Activity> list(User user) {
    return activities.findByUserOrderByTimestampDesc(user);
  }

  public double total(User user) {
    return list(user).stream().mapToDouble(Activity::getEmission).sum();
  }
}
