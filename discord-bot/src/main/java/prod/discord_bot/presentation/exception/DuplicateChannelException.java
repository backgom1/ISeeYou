package prod.discord_bot.presentation.exception;

public class DuplicateChannelException extends RuntimeException {
    private static final String MESSAGE = "이미 모니터링이 실행 중입니다.";

    public DuplicateChannelException() {
        super(MESSAGE);
    }
}
