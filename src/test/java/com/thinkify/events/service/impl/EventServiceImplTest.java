package com.thinkify.events.service.impl;

import com.thinkify.events.entity.Event;
import com.thinkify.events.exception.EventNotFoundException;
import com.thinkify.events.model.object.LocationType;
import com.thinkify.events.model.request.EventRequest;
import com.thinkify.events.model.response.EventResponse;
import com.thinkify.events.repo.EventRepo;
import com.thinkify.events.utils.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepo eventRepo;
    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void find_all_should_return_whole_list(){
        //given
        Event event = Event.builder()
                .id(1)
                .description("testing")
                .location("noida")
                .locationType(LocationType.ONSITE)
                .name("go-dev meetup")
                .startTime(new Date())
                .endTime(new Date())
                .build();

        //when
        when(eventRepo.findAll()).thenReturn(List.of(event));
        List<EventResponse> fetchedEvents = eventService.fetchAllEvents();

        //then
        assertEquals(List.of(Mapper.convertToEventResponse(event)), fetchedEvents);
    }

    @Test
    void find_a_single_event() throws EventNotFoundException {
        Event event = Event.builder()
                .id(1)
                .description("testing")
                .location("noida")
                .locationType(LocationType.ONSITE)
                .name("go-dev meetup")
                .startTime(new Date())
                .endTime(new Date())
                .build();

        //when
        when(eventRepo.findById(anyLong())).thenReturn(Optional.of(event));
        EventResponse fetchedEventResponse = eventService.findEvent(1L);

        //then
        assertEquals(fetchedEventResponse, Mapper.convertToEventResponse(event));
    }

    @Test
    void save_an_event(){
        // given
        EventRequest request = EventRequest.builder()
                .description("testing")
                .location("noida")
                .locationType(LocationType.ONSITE)
                .name("go-dev meetup")
                .startTime(new Date())
                .endTime(new Date())
                .build();

        //when
        Event event = Mapper.convertToEvent(request);
        when(eventRepo.save(event)).thenReturn(event);

        //then
        event.setId(1L);
        EventResponse response = Mapper.convertToEventResponse(event);
        assertEquals(response.getName(), Mapper.convertToEventResponse(eventRepo.save(event)).getName());
    }
}