package prod.discord_bot.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prod.discord_bot.domain.user.UserMonitor;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.LeagueEntryDto;
import prod.discord_bot.dto.StartMonitoringResult;
import prod.discord_bot.dto.SummonerDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.spectator.dto.SpectatorDto;
import prod.discord_bot.dto.spectator.request.SpectatorRequest;
import prod.discord_bot.infra.repository.ChannelUserRepository;
import prod.discord_bot.infra.repository.RiotApiRepository;
import prod.discord_bot.presentation.exception.MaxSetUserException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordMonitorDomainService {

    private final RiotApiRepository riotApiRepository;
    private final ChannelUserRepository channelUserRepository;

    public StartMonitoringResult startMonitoring(String message, String channelId) {

        int maxCount = channelUserRepository.countByChannelId(channelId);
        if (maxCount >= 5) {
            throw new MaxSetUserException();
        }

        String[] parts = message.split(" ");

        if (parts.length < 2) {
            return StartMonitoringResult.failure("사용법: `!감시 게임이름#태그`");
        }

        String riotId = parts[1];
        String[] riotIdParts = riotId.split("#");

        if (riotIdParts.length < 2) {
            return StartMonitoringResult.failure("올바른 형식: `게임이름#태그`를 입력해주세요!");
        }

        String gameName = riotIdParts[0];
        String tagLine = riotIdParts[1];

        AccountDto account = riotApiRepository.getAccountByUsername(new AccountRequest(gameName, tagLine));
        SummonerDto tftSummoner = riotApiRepository.getTFTSummoner(account.getPuuid());
        LeagueEntryDto tftLeagueStat = riotApiRepository.getTFTLeagueStat(tftSummoner.getId());

        UserMonitor.create(account,tftSummoner,tftLeagueStat);



        return StartMonitoringResult.success("소환사 감시를 정상적으로 등록했습니다.");
    }
}
