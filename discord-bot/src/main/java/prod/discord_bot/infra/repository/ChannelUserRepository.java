package prod.discord_bot.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prod.discord_bot.domain.channel.ChannelUser;
import prod.discord_bot.dto.MonitorUserDto;

import java.util.List;

public interface ChannelUserRepository extends JpaRepository<ChannelUser, Long> {

    int countByChannelId(String channelId);

    @Query("select new prod.discord_bot.dto.MonitorUserDto(um.userId, um.userTag) from ChannelUser cu " +
            "join UserMonitor um on um.id = cu.userMonitor.id " +
            "where cu.channelId =:channelId")
    List<MonitorUserDto> findMonitorUsersByChannelId(@Param("channelId") String channelId);
}
