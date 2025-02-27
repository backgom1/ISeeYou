package prod.discord_bot.dto.spectator.request;

import lombok.Getter;

@Getter
public class SpectatorRequest {
    private final String encryptedPUUID;

    public SpectatorRequest(String encryptedPUUID) {
        this.encryptedPUUID = encryptedPUUID;
    }
}
