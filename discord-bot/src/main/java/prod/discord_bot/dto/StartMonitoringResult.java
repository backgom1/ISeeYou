package prod.discord_bot.dto;

import lombok.Getter;

@Getter
public class StartMonitoringResult {

    private final boolean success;
    private final String message;

    private StartMonitoringResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static StartMonitoringResult success(String message) {
        return new StartMonitoringResult(true, message);
    }

    public static StartMonitoringResult failure(String message) {
        return new StartMonitoringResult(false, message);
    }


}
