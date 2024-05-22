package com.thinkify.events.service;

import com.thinkify.events.entity.Event;
import com.thinkify.events.exception.EventNotFoundException;

import java.util.List;

public interface EventService {
    Event saveEvent(Event event);
    Boolean deleteEvent(Long id) throws EventNotFoundException;
    Event findEvent(Long id) throws EventNotFoundException;
    List<Event> fetchAllEvents();
    Event updateEvent(Long id, Event updatedEvent) throws EventNotFoundException;
}
