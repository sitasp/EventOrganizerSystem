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
    public Event save(Event event) {
        return eventRepo.save(event);
    }

    @Override
    public Boolean deleteById(Long id) throws EventNotFoundException{
        Event event = findById(id);
        eventRepo.delete(event);
        return true;
    }

    @Override
    public Event findById(Long id) throws EventNotFoundException{
        Optional<Event> eventOptional = eventRepo.findById(id);
        if(eventOptional.isPresent()){
            return eventOptional.get();
        }
        else throw new EventNotFoundException();
    }

    @Override
    public List<Event> findAll() {
        return eventRepo.findAll();
    }
}
