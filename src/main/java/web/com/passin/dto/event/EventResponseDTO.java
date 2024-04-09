package web.com.passin.dto.event;

import lombok.Data;
import web.com.passin.domain.event.Event;
@Data
public class EventResponseDTO {
    public EventDetailDTO event;

    public EventResponseDTO(Event event, Integer numberOfAttendees){
        this.event = new EventDetailDTO(event.getId(), event.getTitle(), event.getDetails(), event.getSlug(), event.getMaximumAttendees(), numberOfAttendees);

    }
}
