package web.com.passin.domain.attendee;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.Data;
import web.com.passin.domain.event.Event;

@Table(name = "attendees")
@Entity(name = "attendees")
@Data
public class Attendee {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false, length = 255)
    private String email;
    @ManyToOne 
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @CreationTimestamp 
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}