package prod.discord_bot.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prod.discord_bot.domain.channel.ChannelUser;

public interface ChannelUserRepository extends JpaRepository<ChannelUser, Long> {

    int countByChannelId(String channelId);
}
