package com.thinkify.events.controller.openAPI;

import com.thinkify.events.entity.Event;
import com.thinkify.events.model.request.EventRequest;
import com.thinkify.events.model.response.BaseResponse;
import com.thinkify.events.model.response.EventResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Event", description = "Event CRUD API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized, Try with a fresh JWT token"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error"),
        @ApiResponse(responseCode = "403", description = "Access Denied, Forbidden")
})
public interface EventAPIDocable {

    @Operation(summary = "Fetch All Events",
            description = "Fetches all Event Entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all Events")
    })
    @Parameters({
            @Parameter(description = "Email for verification", required = true, in = ParameterIn.HEADER)
    })
    ResponseEntity<List<EventResponse>> getAllEvents();


    @Operation(summary = "Get an event by ID", description = "Fetches an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found and returned"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @Parameters({
            @Parameter(description = "Email for verification", required = true, in = ParameterIn.HEADER)
    })
    ResponseEntity<BaseResponse> getEvent(long eventId);

    @Operation(summary = "Create a new event", description = "Creates a new event and returns the created event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Parameters({
            @Parameter(description = "Email for verification", required = true, in = ParameterIn.HEADER)
    })
    ResponseEntity<BaseResponse> createEvent(EventRequest eventRequest);

    @Operation(summary = "Update an event", description = "Updates an existing event with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "400", description = "Event not found or invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Parameters({
            @Parameter(description = "Email for verification", required = true, in = ParameterIn.HEADER)
    })
    ResponseEntity<BaseResponse> updateEvent(long eventId, EventRequest changed);

    @Operation(summary = "Delete an event", description = "Deletes an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Event not found or could not be deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Parameters({
            @Parameter(description = "Email for verification", required = true, in = ParameterIn.HEADER)
    })
    ResponseEntity<BaseResponse> deleteEvent(long eventId);
}
