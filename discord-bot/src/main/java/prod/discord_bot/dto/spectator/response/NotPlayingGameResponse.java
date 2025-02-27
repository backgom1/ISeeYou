package prod.discord_bot.dto.spectator.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotPlayingGameResponse {
    private int httpStatus;
    private String errorCode;
    private String message;
    private String implementationDetails;

    @Builder
    private NotPlayingGameResponse(int httpStatus, String errorCode, String message, String implementationDetails) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
        this.implementationDetails = implementationDetails;
    }
}
