package prod.discord_bot.infra.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import prod.discord_bot.dto.AccountDto;
import prod.discord_bot.dto.request.AccountRequest;
import prod.discord_bot.infra.config.RiotConfig;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RiotApiRepository {

    private final RiotConfig riotConfig;

    public AccountDto getAccountByUsername(AccountRequest request) {
        return riotConfig.baseClient().get()
                .uri("riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}", request.getGameName(), request.getTagLine())
                .retrieve()
                .body(AccountDto.class);
    }

}
