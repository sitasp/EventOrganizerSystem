package com.thinkify.events.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkify.events.model.object.LocationType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class EventRequest {
    private String name;
    private String description;
    private String location;
    private LocationType locationType;
    private Date startTime;
    private Date endTime;
}
