package prod.discord_bot.domain.global;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @DateTimeFormat(style = "YYYY-MM-DD hh:mm:ss")
    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @DateTimeFormat(style = "YYYY-MM-DD hh:mm:ss")
    @Column(name = "update_at")
    private LocalDateTime updatedAt;
}
