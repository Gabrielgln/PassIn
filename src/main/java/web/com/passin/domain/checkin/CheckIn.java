package web.com.passin.domain.checkin;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.Data;
import web.com.passin.domain.attendee.Attendee;

@Table(name = "check_ins")
@Entity(name = "check_ins")
@Data
public class CheckIn {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp 
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @OneToOne 
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;
}
