package com.example.saferide.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSuccessOrder {
    ResOrder data;

    @Getter
    @Setter
    public static class ResOrder {
        private String msg;
        private String maHD;
    }

}