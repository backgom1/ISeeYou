package prod.discord_bot.presentation.exception;

public class NotPlayingGameException extends RuntimeException {

    private static final String MESSAGE = "좋은 현생을 살고있습니다.";

    public NotPlayingGameException() {
        super(MESSAGE);
    }
}
