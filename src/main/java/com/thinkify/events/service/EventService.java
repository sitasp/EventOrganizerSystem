package com.thinkify.events.service;

import com.thinkify.events.entity.Event;
import com.thinkify.events.exception.EventNotFoundException;
import com.thinkify.events.model.request.EventRequest;
import com.thinkify.events.model.response.BaseResponse;
import com.thinkify.events.model.response.EventResponse;

import java.util.List;

public interface EventService {
    EventResponse saveEvent(EventRequest eventRequest);
    BaseResponse deleteEvent(Long id) throws EventNotFoundException;
    EventResponse findEvent(Long id) throws EventNotFoundException;
    List<EventResponse> fetchAllEvents();
    EventResponse updateEvent(Long id, EventRequest updatedEvent) throws EventNotFoundException;
}
