package prod.discord_bot.presentation.exception;

public class NotFoundTFTSummonerException extends RuntimeException {
    private static final String MESSAGE = "존재하지 않는 소환사 명입니다.";

    public NotFoundTFTSummonerException() {
        super(MESSAGE);
    }
}
