package prod.discord_bot.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prod.discord_bot.domain.channel.ChannelUser;
import prod.discord_bot.domain.user.UserMonitor;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.LeagueEntryDto;
import prod.discord_bot.dto.DiscordMessageResult;
import prod.discord_bot.dto.SummonerDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.infra.repository.ChannelUserRepository;
import prod.discord_bot.dto.MonitorUserDto;
import prod.discord_bot.infra.repository.RiotApiRepositoryV2;
import prod.discord_bot.infra.repository.UserMonitorRepository;
import prod.discord_bot.presentation.exception.MaxSetUserException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordMonitorDomainService {

    private final RiotApiRepositoryV2 riotApiRepositoryV2;
    private final ChannelUserRepository channelUserRepository;
    private final UserMonitorRepository userMonitorRepository;


    @Transactional
    public DiscordMessageResult<Void> startMonitoring(String message, String channelId) {

        int maxCount = channelUserRepository.countByChannelId(channelId);
        if (maxCount >= 5) {
            throw new MaxSetUserException();
        }

        String[] parts = message.split(" ");

        if (parts.length < 2) {
            return DiscordMessageResult.failure("사용법: `!감시 소환사 명#태그`");
        }

        String riotId = parts[1];
        String[] riotIdParts = riotId.split("#");

        if (riotIdParts.length < 2) {
            return DiscordMessageResult.failure("올바른 형식: `소환사 명#태그`를 입력해주세요!");
        }

        String gameName = riotIdParts[0];
        String tagLine = riotIdParts[1];

        AccountDto account = riotApiRepositoryV2.getAccountByUsername(new AccountRequest(gameName, tagLine));
        SummonerDto tftSummoner = riotApiRepositoryV2.getTFTSummoner(account.getPuuid());
        List<LeagueEntryDto> tftLeagueStat = riotApiRepositoryV2.getTFTLeagueStat(tftSummoner.getId());

        for (LeagueEntryDto leagueEntryDto : tftLeagueStat) {
            UserMonitor userMonitor = UserMonitor.create(account, tftSummoner, leagueEntryDto);
            UserMonitor savedUserMonitor = userMonitorRepository.save(userMonitor);
            ChannelUser channelUser = ChannelUser.create(channelId, savedUserMonitor);
            channelUserRepository.save(channelUser);
        }

        return DiscordMessageResult.success("소환사 감시를 정상적으로 등록했습니다.");
    }


    @Transactional(readOnly = true)
    public DiscordMessageResult<String> findMonitorUsersByChannelId(String channelId) {
        List<MonitorUserDto> monitorUsersByChannelId = channelUserRepository.findMonitorUsersByChannelId(channelId);
        StringBuilder result = new StringBuilder();
        for (int index = 1; index <= monitorUsersByChannelId.size(); index++) {
            MonitorUserDto user = monitorUsersByChannelId.get(index - 1);
            if (index == monitorUsersByChannelId.size()) {
                result.append(index).append(".").append(user.getUserId()).append("#").append(user.getUserTag());
            } else {
                result.append(index).append(".").append(user.getUserId()).append("#").append(user.getUserTag()).append("\n");
            }

        }
        return DiscordMessageResult.success("감시 목록을 조회했습니다.", result.toString());
    }
}
