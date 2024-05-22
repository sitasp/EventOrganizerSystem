package com.thinkify.events.repo;

import com.thinkify.events.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
}
