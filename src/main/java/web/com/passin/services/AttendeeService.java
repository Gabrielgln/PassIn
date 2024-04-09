package web.com.passin.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import web.com.passin.repositories.AttendeeRepository;
import web.com.passin.repositories.CheckInRepository;
import web.com.passin.domain.attendee.Attendee;
import web.com.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import web.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import web.com.passin.domain.checkin.CheckIn;
import web.com.passin.dto.attendee.AttendeeBadgeDTO;
import web.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
import web.com.passin.dto.attendee.AttendeeDetailDTO;
import web.com.passin.dto.attendee.AttendeeListResponseDTO;


@Service
@RequiredArgsConstructor
public class AttendeeService {
    //final é para criar uma atributo constante, significa que não vai ser alterado o valor do atributo
    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetailDTO> attendeeDetailList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
            return new AttendeeDetailDTO(
                attendee.getId(), 
                attendee.getName(), 
                attendee.getEmail(), 
                attendee.getCreatedAt(), 
                checkedInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailList);
    }

    public void verifyAttendeeSubscription(String email, String eventId){
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered");
    }

    public Attendee registerAttendee(Attendee newAttendee){
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public void checkInAttendee(String attendeId){
        this.checkInService.registerCheckIn(this.getAttendee(attendeId));
    }

    public Attendee getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID:" + attendeeId));
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        Attendee attendee = this.getAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());

        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);        
    }

    
}
