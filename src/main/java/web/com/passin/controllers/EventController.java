package web.com.passin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import lombok.Data;
import web.com.passin.dto.attendee.AttendeeIdDTO;
import web.com.passin.dto.attendee.AttendeeListResponseDTO;
import web.com.passin.dto.attendee.AttendeeRequestDTO;
import web.com.passin.dto.event.EventIdDTO;
import web.com.passin.dto.event.EventRequestDTO;
import web.com.passin.dto.event.EventResponseDTO;
import web.com.passin.services.AttendeeService;
import web.com.passin.services.EventService;

@RestController
@RequestMapping("/events")
@Data
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id){
        EventResponseDTO event = this.eventService.getEventDetail(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerPartipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO attendeeRequestDTO, UriComponentsBuilder uriComponentsBuilder){
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId,attendeeRequestDTO);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();
        
        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }


    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String id){
        return ResponseEntity.ok(this.attendeeService.getEventsAttendee(id));
    }
}
