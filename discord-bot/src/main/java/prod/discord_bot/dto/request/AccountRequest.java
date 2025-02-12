package prod.discord_bot.dto.request;


import lombok.Getter;

@Getter
public class AccountRequest {

    private final String gameName;
    private final String tagLine;

    public AccountRequest(String gameName, String tagLine) {
        this.gameName = gameName;
        this.tagLine = tagLine;
    }
}
