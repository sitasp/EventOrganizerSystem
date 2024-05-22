package com.thinkify.events.service.impl;

import com.thinkify.events.entity.Event;
import com.thinkify.events.exception.EventNotFoundException;
import com.thinkify.events.repo.EventRepo;
import com.thinkify.events.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EventServiceImpl implements EventService {

    private Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepo eventRepo;

    @Override
    public Event saveEvent(Event event) {
        LOGGER.info("Saving event to Database: {}", event);
        return eventRepo.save(event);
    }

    @Override
    public Boolean deleteEvent(Long id) throws EventNotFoundException{
        Event event = findEvent(id);
        LOGGER.info("Event retrieved with ID: {}, will be deleted.", id);
        eventRepo.delete(event);
        LOGGER.info("Event with ID: {}, deleted successfully", id);
        return true;
    }

    @Override
    public Event findEvent(Long id) throws EventNotFoundException{
        Optional<Event> eventOptional = eventRepo.findById(id);
        LOGGER.info("Fetching event with ID: {}", id);
        if(eventOptional.isPresent()){
            LOGGER.info("Found event with ID: {}", id);
            return eventOptional.get();
        }
        else throw new EventNotFoundException();
    }

    @Override
    public List<Event> fetchAllEvents() {
        LOGGER.info("Fetching all events.");
        return eventRepo.findAll();
    }

    @Override
    public Event updateEvent(Long id, Event updatedEvent) throws EventNotFoundException {
        LOGGER.info("Updating event of id: {} to {}", id, updatedEvent);
        return eventRepo.findById(id).map(event -> {
            event.setName(updatedEvent.getName());
            event.setDescription(updatedEvent.getDescription());
            event.setLocation(updatedEvent.getLocation());
            event.setStartTime(updatedEvent.getStartTime());
            event.setEndTime(updatedEvent.getEndTime());
            return eventRepo.save(event);
        }).orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));
    }
}
