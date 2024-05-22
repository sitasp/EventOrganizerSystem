package com.thinkify.events.controller;

import com.thinkify.events.entity.Event;
import com.thinkify.events.exception.EventNotFoundException;
import com.thinkify.events.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> events = eventService.findAll();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable(value = "eventId") long eventId){
        try{
            Event event = eventService.findById(eventId);
            LOGGER.info("Event retrieved with id: " + eventId);
            return ResponseEntity.status(HttpStatus.OK.value()).body(event);
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        Event createdEvent = eventService.save(event);
        LOGGER.info("Event created with id: " + createdEvent);
        return ResponseEntity.ok(createdEvent);
    }
}
