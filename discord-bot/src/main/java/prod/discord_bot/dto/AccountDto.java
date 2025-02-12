package prod.discord_bot.dto;

import lombok.Getter;

@Getter
public class AccountDto {

    private final String puuid;
    private final String gameName;
    private final String tagLine;

    public AccountDto(String puuid, String gameName, String tagLine) {
        this.puuid = puuid;
        this.gameName = gameName;
        this.tagLine = tagLine;
    }
}
