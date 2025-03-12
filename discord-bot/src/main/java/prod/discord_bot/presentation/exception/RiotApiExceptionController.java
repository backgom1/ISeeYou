package prod.discord_bot.presentation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RiotApiExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotPlayingGameException.class)
    public ApiErrorResponse<Void> notPlayingGameException(NotPlayingGameException e) {
        log.info("{}", e.getMessage());
        return ApiErrorResponse.error("GOOD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundTFTSummonerException.class)
    public ApiErrorResponse<Void> notFoundTFTSummoner(NotFoundTFTSummonerException e) {
        log.info("{}", e.getMessage());
        return ApiErrorResponse.error("GOOD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MaxSetUserException.class)
    public ApiErrorResponse<Void> maxSetUserException(MaxSetUserException e) {
        log.info("{}", e.getMessage());
        return ApiErrorResponse.error("GOOD", e.getMessage());
    }

}
