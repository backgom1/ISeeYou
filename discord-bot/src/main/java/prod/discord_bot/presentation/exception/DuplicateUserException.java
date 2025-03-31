package prod.discord_bot.presentation.exception;

public class DuplicateUserException extends RuntimeException {
    private static final String MESSAGE = "채널에 등록된 소환사입니다.";

    public DuplicateUserException() {
        super(MESSAGE);
    }
}
