package com.example.saferide.response;

import java.util.List;

public class APIBaseResponse<T> {
    int status;
    List<T> data;
    String error;
    String msg;

    public APIBaseResponse(int i, String msg, T data, Object o) {

    }

    public static <T> APIBaseResponse<T> success(T data, String message) {
        return new APIBaseResponse<>(200, message, data, null);
    }

    // Phương thức tạo response lỗi
    public static <T> APIBaseResponse<T> error(int status, String errorMessage) {
        return new APIBaseResponse<>(status, "Error", null, errorMessage);
    }

}
