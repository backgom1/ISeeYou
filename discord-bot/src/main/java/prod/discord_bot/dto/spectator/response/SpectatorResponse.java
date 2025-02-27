package prod.discord_bot.dto.spectator.response;

import lombok.Getter;

@Getter
public class SpectatorResponse {
    private final String status;
    private final int gameLength;

    private SpectatorResponse(String status, int gameLength) {
        this.status = status;
        this.gameLength = gameLength;
    }

    public static SpectatorResponse ok(String status, int gameLength) {
        return new SpectatorResponse(status, gameLength);
    }
}
