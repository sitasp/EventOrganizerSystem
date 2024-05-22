package com.thinkify.events.utils;

import com.thinkify.events.entity.Event;
import com.thinkify.events.model.request.EventRequest;
import com.thinkify.events.model.response.EventResponse;

public class Mapper {

    public static Event convertToEvent(EventRequest eventRequest) {
        Event event = new Event();

        event.setName(eventRequest.getName());
        event.setDescription(eventRequest.getDescription());
        event.setLocation(eventRequest.getLocation());
        event.setLocationType(eventRequest.getLocationType());
        event.setStartTime(eventRequest.getStartTime());
        event.setEndTime(eventRequest.getEndTime());

        return event;
    }

    public static EventResponse convertToEventResponse(Event event) {
        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(event.getId());
        eventResponse.setName(event.getName());
        eventResponse.setDescription(event.getDescription());
        eventResponse.setLocation(event.getLocation());
        eventResponse.setLocationType(event.getLocationType());
        eventResponse.setStartTime(event.getStartTime());
        eventResponse.setEndTime(event.getEndTime());
        return eventResponse;
    }
}
