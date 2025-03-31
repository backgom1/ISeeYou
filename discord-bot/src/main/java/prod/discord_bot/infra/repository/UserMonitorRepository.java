package prod.discord_bot.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prod.discord_bot.domain.user.UserMonitor;

public interface UserMonitorRepository extends JpaRepository<UserMonitor, Long> {

}
