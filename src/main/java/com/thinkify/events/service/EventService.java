package com.thinkify.events.service;

import com.thinkify.events.entity.Event;
import com.thinkify.events.exception.EventNotFoundException;

import java.util.List;

public interface EventService {
    Event save(Event event);
    Boolean deleteById(Long id) throws EventNotFoundException;
    Event findById(Long id) throws EventNotFoundException;
    List<Event> findAll();
}
