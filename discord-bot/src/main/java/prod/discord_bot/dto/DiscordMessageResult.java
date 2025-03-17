package prod.discord_bot.dto;

import lombok.Getter;

@Getter
public class DiscordMessageResult<T> {

    private final boolean success;
    private final String message;
    private final T data;

    private DiscordMessageResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> DiscordMessageResult<T> success(String message, T data) {
        return new DiscordMessageResult<>(true, message, data);
    }

    public static <T> DiscordMessageResult<T> success(String message) {
        return new DiscordMessageResult<>(true, message, null);
    }

    public static <T> DiscordMessageResult<T> failure(String message) {
        return new DiscordMessageResult<>(false, message, null);
    }


}
