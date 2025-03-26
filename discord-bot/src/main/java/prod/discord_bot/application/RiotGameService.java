package prod.discord_bot.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.spectator.dto.SpectatorDto;
import prod.discord_bot.dto.spectator.request.SpectatorRequest;
import prod.discord_bot.dto.spectator.response.SpectatorResponse;
import prod.discord_bot.infra.repository.RiotApiRepositoryV1;
import prod.discord_bot.infra.repository.RiotApiRepositoryV2;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiotGameService {

    private final RiotApiRepositoryV1 riotApiRepositoryV1;


    public SpectatorResponse getCurrentGame(AccountRequest request) {
        AccountDto account = riotApiRepositoryV1.getAccountByUsername(request);
        SpectatorDto game = riotApiRepositoryV1.getSpectatorGame(new SpectatorRequest(account.getPuuid()));
        return SpectatorResponse.ok(game.getGameType(),game.getGameLength());
    }
}
