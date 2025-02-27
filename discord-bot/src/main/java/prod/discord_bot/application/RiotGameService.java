package prod.discord_bot.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.spectator.dto.SpectatorDto;
import prod.discord_bot.dto.spectator.request.SpectatorRequest;
import prod.discord_bot.dto.spectator.response.SpectatorResponse;
import prod.discord_bot.infra.repository.RiotApiRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiotGameService {

    private final RiotApiRepository riotApiRepository;

    public SpectatorResponse getCurrentGame(AccountRequest request) {
        AccountDto account = riotApiRepository.getAccountByUsername(request);
        SpectatorDto game = riotApiRepository.getSpectatorGame(new SpectatorRequest(account.getPuuid()));
        return SpectatorResponse.ok(game.getGameType(),game.getGameLength());
    }
}
