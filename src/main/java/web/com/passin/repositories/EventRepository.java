package web.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.com.passin.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, String>{

}
