package prod.discord_bot.infra.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.LeagueEntryDto;
import prod.discord_bot.dto.SummonerDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.spectator.dto.SpectatorDto;
import prod.discord_bot.dto.spectator.request.SpectatorRequest;
import prod.discord_bot.infra.config.RiotConfig;
import prod.discord_bot.presentation.exception.NotFoundTFTSummonerException;
import prod.discord_bot.presentation.exception.NotPlayingGameException;

import java.util.List;

/**
 * REST API 통신에 필요한 레포지토리 클래스
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RiotApiRepositoryV1 {

    private final RiotConfig riotConfig;

    public AccountDto getAccountByUsername(AccountRequest request) {
        return riotConfig.baseClientV1().get()
                .uri("riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}", request.getGameName(), request.getTagLine())
                .retrieve()
                .body(AccountDto.class);
    }

    public SpectatorDto getSpectatorGame(SpectatorRequest request) {
        return riotConfig.baseClientV5().get()
                .uri("lol/spectator/tft/v5/active-games/by-puuid/{encryptedPUUID}", request.getEncryptedPUUID())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((httpRequest, response) -> {
                            throw new NotPlayingGameException();
                }))
                .body(SpectatorDto.class);
    }


    public SummonerDto getTFTSummoner(String puuid) {
        return riotConfig.baseClientV5().get()
                .uri("tft/summoner/v1/summoners/by-puuid/{encryptedPUUID}", puuid)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((httpRequest, response) -> {
                    throw new NotFoundTFTSummonerException();
                }))
                .body(SummonerDto.class);
    }


    public List<LeagueEntryDto> getTFTLeagueStat(String summonerId) {

        LeagueEntryDto[] body = riotConfig.baseClientV5().get()
                .uri("tft/league/v1/entries/by-summoner/{summonerId}", summonerId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((httpRequest, response) -> {
                    throw new NotFoundTFTSummonerException();
                }))
                .body(LeagueEntryDto[].class);

        return List.of(body);
    }






}
