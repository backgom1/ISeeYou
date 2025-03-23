package prod.discord_bot.domain.monitor;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prod.discord_bot.domain.global.BaseEntity;

@Getter
@Entity
@Table(name = "monitoring_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonitoringSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String channelId;


    private MonitoringSchedule(String channelId) {
        this.channelId = channelId;
    }

    public static MonitoringSchedule create(String channelId) {
        return new MonitoringSchedule(channelId);
    }
}
