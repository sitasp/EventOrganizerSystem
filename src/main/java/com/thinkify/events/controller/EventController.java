package com.thinkify.events.controller;

import com.thinkify.events.controller.openAPI.EventAPIDocable;
import com.thinkify.events.entity.Event;
import com.thinkify.events.exception.EventNotFoundException;
import com.thinkify.events.model.request.EventRequest;
import com.thinkify.events.model.response.BaseResponse;
import com.thinkify.events.model.response.EventResponse;
import com.thinkify.events.service.EventService;
import com.thinkify.events.utils.EOSConstants;
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
    public ResponseEntity<List<EventResponse>> getAllEvents(){
        List<EventResponse> eventResponses = eventService.fetchAllEvents();
        return ResponseEntity.ok(eventResponses);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<BaseResponse> getEvent(@PathVariable(value = "id") long eventId){
        BaseResponse baseResponse = null;
        try{
            baseResponse = eventService.findEvent(eventId);
            baseResponse.setStatusCode(HttpStatus.OK.value());
            baseResponse.setMessage(EOSConstants.SUCCESS);
        } catch (EventNotFoundException e) {
            LOGGER.error("No event found with ID: " + eventId);
            baseResponse = new BaseResponse("No event found with ID: " + eventId);
            baseResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage(e.getMessage());
        }
        return ResponseEntity.status(baseResponse.getStatusCode()).body(baseResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createEvent(@RequestBody EventRequest eventRequest){
        BaseResponse createdEvent = eventService.saveEvent(eventRequest);
        createdEvent.setMessage(EOSConstants.SUCCESS);
        LOGGER.info("Event created with id: " + createdEvent);
        return ResponseEntity.ok(createdEvent);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse> updateEvent(@PathVariable(value = "id") long eventId, @RequestBody EventRequest changed){
        BaseResponse response = null;
        try{
            response = eventService.updateEvent(eventId, changed);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage(EOSConstants.SUCCESS);
        } catch (EventNotFoundException e) {
            response = new BaseResponse();
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteEvent(@PathVariable(value = "id") long eventId){
        BaseResponse response = null;
        try{
            response = eventService.deleteEvent(eventId);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage(EOSConstants.SUCCESS);
        } catch (EventNotFoundException e) {
            LOGGER.error("Event could not be deleted: " + e.getMessage());
            response = new BaseResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
