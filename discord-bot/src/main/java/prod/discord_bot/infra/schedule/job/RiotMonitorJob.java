package prod.discord_bot.infra.schedule.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.MonitorUserDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.spectator.dto.SpectatorDto;
import prod.discord_bot.dto.spectator.request.SpectatorRequest;
import prod.discord_bot.infra.repository.ChannelUserRepository;
import prod.discord_bot.infra.repository.RiotApiRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RiotMonitorJob implements Job {

    private JDA jda;
    private final RiotApiRepository riotApiRepository;
    private final ChannelUserRepository channelUserRepository;

    @Override
    public void execute(JobExecutionContext context) {


        JobDataMap dataMap = context.getMergedJobDataMap();

        String channelId = dataMap.getString("channelId");

        log.info("채널 모니터링 시작 -> {}", channelId);

        List<MonitorUserDto> users = channelUserRepository.findMonitorUsersByChannelId(channelId);

        for (MonitorUserDto user : users) {
            AccountRequest request = new AccountRequest(user.getUserId(), user.getUserTag());
            AccountDto account = riotApiRepository.getAccountByUsername(request);
            SpectatorDto game = riotApiRepository.getSpectatorGame(new SpectatorRequest(account.getPuuid()));
        }

        //스케줄링 동작 즉 한 채널에서 사용자가 전부 사용을 하고있지않는다면, 모니터링 스케줄링 종료


        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel != null) {
//            channel.sendMessage("현재 게임 정보: " + gameInfo).queue();
        } else {
            System.out.println("❌ 채널을 찾을 수 없습니다: " + channelId);
        }
    }
}
