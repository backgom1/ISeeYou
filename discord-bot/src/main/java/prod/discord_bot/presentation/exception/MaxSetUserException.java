package prod.discord_bot.presentation.exception;

public class MaxSetUserException extends RuntimeException {

    private static final String MESSAGE = "채널당 사용자는 5명 이상을 넘을 수 없습니다.";

    public MaxSetUserException() {
        super(MESSAGE);
    }
}
