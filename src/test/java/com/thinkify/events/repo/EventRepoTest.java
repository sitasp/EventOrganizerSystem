package com.thinkify.events.repo;

import com.thinkify.events.entity.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EventRepoTest {

    @Autowired
    private EventRepo eventRepo;

    @Test
    public void testSaveEvent(){
        Event event = new Event();
        event.setName("Test Event");
        event.setDescription("This is a test event");
        event.setLocation("Test Location");
        event.setStartTime(new Date());
        event.setEndTime(new Date());
        eventRepo.save(event);
    }
}