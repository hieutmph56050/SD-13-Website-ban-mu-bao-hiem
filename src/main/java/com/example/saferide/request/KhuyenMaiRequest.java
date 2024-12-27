package com.example.saferide.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class KhuyenMaiRequest {

    private String ma;
    private String ten;
    private BigDecimal giaTri;
    private LocalDateTime ngayBD;
    private LocalDateTime ngayKT;
    private String PTKM;
    private String DKKM;
    private String nguoiTao;
    private String nguoiCapNhat;
    private String tt;
    private List<Integer> productDetailIds;
}
