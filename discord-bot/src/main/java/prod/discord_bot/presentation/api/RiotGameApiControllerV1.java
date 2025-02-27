package prod.discord_bot.presentation.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prod.discord_bot.application.RiotGameService;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.dto.spectator.response.SpectatorResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RiotGameApiControllerV1 {
    private final RiotGameService riotGameService;

    @PostMapping("/api/v1/spectator")
    public SpectatorResponse getSpectator(@RequestBody AccountRequest request) {
        return riotGameService.getCurrentGame(request);
    }
}
