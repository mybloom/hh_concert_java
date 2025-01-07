package kr.hhplus.be.server.common.apiresponse;

import lombok.Getter;

@Getter
public class BusinessApiResponse<T> {
    private String code;
    private String message;
    private T data;

    private BusinessApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> BusinessApiResponse<T> success(String code, String message, T data) {
        return new BusinessApiResponse<>(code, message, data);
    }

    public static <T> BusinessApiResponse<T> success(String code, T data) {
        return new BusinessApiResponse<>(code, "success", data);
    }

    public static <T> BusinessApiResponse<T> success(T data) {
        return new BusinessApiResponse<>("200", "success", data);
    }

    public static <T> BusinessApiResponse<T> failure(String code, String message) {
        return new BusinessApiResponse<>(code, message, null);
    }
}
