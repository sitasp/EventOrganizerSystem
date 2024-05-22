package com.thinkify.events.controller;

import com.thinkify.events.controller.openAPI.EventAPIDocable;
import com.thinkify.events.entity.Event;
import com.thinkify.events.exception.EventNotFoundException;
import com.thinkify.events.model.response.BaseResponse;
import com.thinkify.events.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController implements EventAPIDocable {

    private Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @GetMapping("/fetch/all")
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> events = eventService.fetchAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable(value = "id") long eventId){
        try{
            Event event = eventService.findEvent(eventId);
            return ResponseEntity.status(HttpStatus.OK.value()).body(event);
        } catch (EventNotFoundException e) {
            LOGGER.error("No event found with ID: " + eventId);
            BaseResponse baseResponse = new BaseResponse("No event found with ID: " + eventId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        Event createdEvent = eventService.saveEvent(event);
        LOGGER.info("Event created with id: " + createdEvent);
        return ResponseEntity.ok(createdEvent);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable(value = "id") long eventId, @RequestBody Event changed){
        try{
            Event updatedEvent = eventService.updateEvent(eventId, changed);
            return ResponseEntity.ok(updatedEvent);
        } catch (EventNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteEvent(@PathVariable(value = "id") long eventId){
        try{
            eventService.deleteEvent(eventId);
            return ResponseEntity.ok(true);
        } catch (EventNotFoundException e) {
            LOGGER.error("Event could not be deleted: " + e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
    }
}
