package com.example.saferide.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HoanTraRequest {

    private Integer hoaDonId;
    private Map<Integer, Integer> sanPhamHoanTra;
    private String lyDo;
}
