package com.example.saferide.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoanTraConfirmationRequest {
    private Integer hoanTraId;  // ID of the return request
    private String  trangThai;  // true for confirmation, false for rejection
}
