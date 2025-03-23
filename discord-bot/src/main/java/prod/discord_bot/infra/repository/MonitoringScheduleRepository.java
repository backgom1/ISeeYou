package prod.discord_bot.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prod.discord_bot.domain.monitor.MonitoringSchedule;

public interface MonitoringScheduleRepository extends JpaRepository<MonitoringSchedule, Long> {

    void deleteByChannelId(String channelId);
}
