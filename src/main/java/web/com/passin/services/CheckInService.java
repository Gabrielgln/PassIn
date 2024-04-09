package web.com.passin.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import web.com.passin.domain.attendee.Attendee;
import web.com.passin.domain.checkin.CheckIn;
import web.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import web.com.passin.repositories.CheckInRepository;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee){
        this.verifyCheckInExists(attendee.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());
        this.checkInRepository.save(newCheckIn);
    }

    private void verifyCheckInExists(String attendeeId){
        Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);

        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId){
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
