package prod.discord_bot.infra.schedule.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.MonitorUserDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.spectator.dto.SpectatorDto;
import prod.discord_bot.dto.spectator.request.SpectatorRequest;
import prod.discord_bot.infra.repository.ChannelUserRepository;
import prod.discord_bot.infra.repository.RiotApiRepositoryV2;
import prod.discord_bot.presentation.exception.NotFoundTFTSummonerException;
import prod.discord_bot.presentation.exception.NotPlayingGameException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RiotMonitorJob implements Job {

    private final JDA jda;
    private final RiotApiRepositoryV2 riotApiRepositoryV2;
    private final ChannelUserRepository channelUserRepository;

    @Override
    public void execute(JobExecutionContext context) {


        JobDataMap dataMap = context.getMergedJobDataMap();

        String channelId = dataMap.getString("channelId");

        log.info("채널 모니터링 시작 -> {}", channelId);

        List<MonitorUserDto> users = channelUserRepository.findMonitorUsersByChannelId(channelId);


        TextChannel channel = jda.getTextChannelById(channelId);

        if (channel != null) {
            for (MonitorUserDto user : users) {
                StringBuilder userBuilder = new StringBuilder();
                try {
                    userBuilder.append(user.getUserId()).append("님의 상태 -> ");
                    AccountRequest request = new AccountRequest(user.getUserId(), user.getUserTag());
                    AccountDto account = riotApiRepositoryV2.getAccountByUsername(request);
                    SpectatorDto game = riotApiRepositoryV2.getSpectatorGame(new SpectatorRequest(account.getPuuid()));
                    userBuilder.append("게임 시간 :").append(game.getGameLength()).append("\n");
                    userBuilder.append("게임 모드 :").append(game.getGameMode()).append("\n");
                    userBuilder.append("게임 시작 시간 :").append(game.getGameStartTime()).append("\n");
                    channel.sendMessage(userBuilder.toString()).queue();
                } catch (NotPlayingGameException | NotFoundTFTSummonerException e) {
                    log.info(e.getMessage());
                    userBuilder.append(e.getMessage()).append("\n");
                    channel.sendMessage(userBuilder).queue();
                }
            }
        }

        //스케줄링 동작 즉 한 채널에서 사용자가 전부 사용을 하고있지않는다면, 모니터링 스케줄링 종료


    }
}
