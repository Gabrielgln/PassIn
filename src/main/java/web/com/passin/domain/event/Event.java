package web.com.passin.domain.event;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "events")
@Entity(name = "events")
@Data
public class Event {
    @Id 
    @Column(nullable = false, length = 255) 
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, length = 255)
    private String title;
    @Column(nullable = false, length = 255)
    private String details;
    @Column(nullable = false, length = 255, unique = true)
    private String slug;
    @Column(name = "maximum_attendees", nullable = false, length = 255)
    private Integer maximumAttendees;
}
