package com.thinkify.events.service.impl;

import com.thinkify.events.entity.Event;
import com.thinkify.events.exception.EventNotFoundException;
import com.thinkify.events.model.request.EventRequest;
import com.thinkify.events.model.response.BaseResponse;
import com.thinkify.events.model.response.EventResponse;
import com.thinkify.events.repo.EventRepo;
import com.thinkify.events.service.EventService;
import com.thinkify.events.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements EventService {

    private Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepo eventRepo;

    @Override
    public EventResponse saveEvent(EventRequest eventRequest) {
        LOGGER.info("Saving event to Database: {}", eventRequest);
        Event event = Mapper.convertToEvent(eventRequest);
        EventResponse response = Mapper.convertToEventResponse(eventRepo.save(event));
        return response;
    }

    @Override
    public BaseResponse deleteEvent(Long id) throws EventNotFoundException{
        Optional<Event> eventOptional = eventRepo.findById(id);
        if(eventOptional.isPresent()){
            LOGGER.info("Event retrieved with ID: {}, will be deleted.", id);
            eventRepo.delete(eventOptional.get());
            LOGGER.info("Event with ID: {}, deleted successfully", id);
            BaseResponse response = new BaseResponse();
            response.setMessage("SUCCESS");
            return response;
        }
        else throw new EventNotFoundException("Event with id: " + id + " not found" );
    }

    @Override
    public EventResponse findEvent(Long id) throws EventNotFoundException{
        Optional<Event> eventOptional = eventRepo.findById(id);
        LOGGER.info("Fetching event with ID: {}", id);
        if(eventOptional.isPresent()){
            LOGGER.info("Found event with ID: {}", id);
            return Mapper.convertToEventResponse(eventOptional.get());
        }
        else throw new EventNotFoundException("Event with id: " + id + " not found" );
    }

    @Override
    public List<EventResponse> fetchAllEvents() {
        LOGGER.info("Fetching all events.");
        List<EventResponse> eventResponses = eventRepo.findAll()
                .stream()
                .map(Mapper::convertToEventResponse).toList();
        return eventResponses;
    }

    @Override
    public EventResponse updateEvent(Long id, EventRequest updatedEventRequest) throws EventNotFoundException {
        LOGGER.info("Updating event of id: {} to {}", id, updatedEventRequest);
        Event updatedEvent = Mapper.convertToEvent(updatedEventRequest);
        Event newEvent = eventRepo.findById(id).map(event -> {
            event.setName(updatedEvent.getName());
            event.setDescription(updatedEvent.getDescription());
            event.setLocation(updatedEvent.getLocation());
            event.setStartTime(updatedEvent.getStartTime());
            event.setEndTime(updatedEvent.getEndTime());
            return eventRepo.save(event);
        }).orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));
        EventResponse newEventResponse = Mapper.convertToEventResponse(newEvent);
        return newEventResponse;
    }
}
