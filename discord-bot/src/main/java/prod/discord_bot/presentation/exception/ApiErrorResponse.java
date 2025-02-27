package prod.discord_bot.presentation.exception;

import lombok.Getter;

@Getter
public abstract class ApiErrorResponse<T> {

    private final String errorCode;
    private final String errorMessage;
    private final T data;


    public ApiErrorResponse(String errorCode, String errorMessage, T data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static <T> ApiErrorResponse<T> error(String errorCode, String errorMessage, T data) {
        return new ApiErrorResponse<T>(errorCode, errorMessage, data) {
        };
    }

    public static <T> ApiErrorResponse<T> error(String errorCode, String errorMessage) {
        return new ApiErrorResponse<T>(errorCode, errorMessage, null) {
        };
    }


}
