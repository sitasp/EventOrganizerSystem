package com.thinkify.events.entity;

import com.thinkify.events.model.object.LocationType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String location;
    @Enumerated
    private LocationType locationType;
    private Date startTime;
    private Date endTime;
}
