package com.thinkify.events.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkify.events.entity.Event;
import com.thinkify.events.model.object.LocationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse extends BaseResponse {
    private long id;
    private String name;
    private String description;
    private String location;
    private LocationType locationType;
    private Date startTime;
    private Date endTime;
}
