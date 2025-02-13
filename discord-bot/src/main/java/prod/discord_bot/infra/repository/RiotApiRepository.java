package prod.discord_bot.infra.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.SpectatorDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.request.SpectatorRequest;
import prod.discord_bot.presentation.exception.NotPlayingGameException;
import prod.discord_bot.infra.config.RiotConfig;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RiotApiRepository {

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




}
