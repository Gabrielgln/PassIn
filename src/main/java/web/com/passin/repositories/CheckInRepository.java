package web.com.passin.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import web.com.passin.domain.checkin.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer>{
    Optional<CheckIn> findByAttendeeId(String attendeId);
}
